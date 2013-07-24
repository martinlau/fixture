/*
 * #%L
 * fixture
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package io.fixture.service

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import javax.mail.Message.RecipientType
import javax.mail.Address
import javax.mail.internet.InternetAddress
import org.apache.velocity.app.VelocityEngine
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.ui.velocity.VelocityEngineUtils
import java.util.HashMap
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.MessageSource

[Service]
class MailServiceImpl [Autowired](

        val mailSender: JavaMailSender,

        val messageSource: MessageSource,

        val velocityEngine: VelocityEngine

): MailService {

    // TODO Test

    // TODO @Value
    val from: String = "us@fixture.io"

    // TODO @Value
    val fromName: String = "fixture.io"

    override fun sendMail(template: String,
                          to: String,
                          name: String,
                          subject: String,
                          model: Map<String, Any?>) {

        val updatedModel = HashMap<String, Any?>(model)

        if (!updatedModel.containsKey("locale"))
            updatedModel.put("locale", LocaleContextHolder.getLocale())

        if (!updatedModel.containsKey("messages"))
            updatedModel.put("messages", messageSource)

        val text = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine,
                template,
                "UTF-8",
                updatedModel
        )

        mailSender.send {
            it.addRecipient(RecipientType.TO, InternetAddress(to, name))
            it.setSender(InternetAddress(from, fromName))
            it.setSubject(subject)
            it.setText(text)
        }
    }

}
