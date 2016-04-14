/*
 * Copyright 2012-2016 the original author or authors.
 *
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
 */

package com.zpl.web;

import com.zpl.model.GenerateRequest;
import com.zpl.model.GenerateResponse;
import com.zpl.model.ReviewDTO;
import com.zpl.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class Controller {

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GenerateResponse generateReviews(@RequestBody final GenerateRequest request) {
        return reviewService.generateReviews(request.getCustCount(),
                request.getReviewCountMin(),
                request.getReviewCountMax());
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET, params = {"customerId"})
    public List<ReviewDTO> loadReviews(@RequestParam(value = "customerId") final Integer customerId) {
        return reviewService.loadReviews(customerId);
    }

}
