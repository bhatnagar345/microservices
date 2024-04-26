package com.harsh.companyms.company.messaging;

import com.harsh.companyms.company.dto.ReviewMessage;
import com.harsh.companyms.company.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage)
    {

        companyService.updateCompanyRating(reviewMessage);
    }

}
