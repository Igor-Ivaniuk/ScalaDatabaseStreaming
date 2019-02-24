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

package com.igori.web;

import com.igori.model.GenerateRequest;
import com.igori.model.GenerateResponse;
import com.igori.model.ReviewDTO;
import com.igori.service.GenerateService;
import com.igori.service.ReviewService;
import com.igori.service_s.ReviewServiceS;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class Controller {

  @Autowired
  private ReviewService reviewService;

  @Autowired
  private ReviewServiceS reviewServiceS;

  @Autowired
  private GenerateService generateReviews;

  @RequestMapping(value = "/generate", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public GenerateResponse generateReviews(@RequestBody final GenerateRequest request) {
    return generateReviews.generateReviews(request.getCustCount(),
        request.getReviewCountMin(),
        request.getReviewCountMax());
  }

  @RequestMapping(value = "/load", method = RequestMethod.GET, params = {"customerId", "useScala"})
  public List<ReviewDTO> loadReviews(@RequestParam(value = "customerId") final Integer customerId,
      @RequestParam(value = "useScala") final Boolean useScala) {
    if (useScala) {
      return reviewServiceS.loadReviews(customerId);
    } else {
      return reviewService.loadReviews(customerId);
    }
  }

}
