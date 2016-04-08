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
import com.zpl.model.ReviewDTO;
import com.zpl.service.ReviewGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class MainController {

    @Autowired
    private ReviewGenerator generator;

    @RequestMapping("/foo")
    public String helloWorld() {
        return "Hello World!";
    }

    @RequestMapping("/bar")
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", "Hello Freemaker!");
        return "welcome";
    }

    @RequestMapping("/resource")
    public ReviewDTO home() {
        ReviewDTO dto = new ReviewDTO();
        Map<String, Object> model = new HashMap<String, Object>();
        dto.setId(1);
        //dto.setReviewText(generator.generateReviewText(20, 50));
//        model.put("id", UUID.randomUUID().toString());
//        model.put("content", "Hello World");
        return dto;
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void generateReviews(@RequestBody final GenerateRequest request) {
        generator.generateReviews(request.getCustomerId(), request.getCount());
        // "Generated " + request.getCount() + " reviews for customer " + request.getCustomerId();
    }

}
