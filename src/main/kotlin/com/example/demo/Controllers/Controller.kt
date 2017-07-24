package com.example.demo.Controllers

import com.example.demo.Models.ModelSMS
import com.twilio.Twilio
import com.twilio.exception.TwilioException
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Controller
class Controller( val ACCOUNT_SID: String = "AC6e10a7a7a5b45f546465e62e624386aa",
                  val AUTH_TOKEN: String = "bf471e22ba407fc8fa159506fa0d6c92",
                  val TWILIO_NUMBER: String = "+12012989241" ) : WebMvcConfigurerAdapter()
{
    override fun addViewControllers ( registry: ViewControllerRegistry ) {
        registry.addViewController("/result").setViewName("result")
        registry.addViewController("/error").setViewName("error")
    }

    @GetMapping("/")
    fun init ( mSMS: Model ) : String {
        mSMS.addAttribute( "sms", ModelSMS() )
        return "index"
    }

    @PostMapping("/")
    fun sendSMS ( sms: ModelSMS ) : String {
        try {
            Twilio.init( ACCOUNT_SID, AUTH_TOKEN )
            var mail: Message = Message.creator( PhoneNumber( sms.phoneNm ),
                    PhoneNumber( TWILIO_NUMBER ),
                    "Message de la part de Mr. BOUGUEDDACH: " + sms.message ).create()
            println( mail.sid )
            return "redirect:/result"
        } catch ( ex: TwilioException ) {
            return "redirect:/error"
        }
    }

}