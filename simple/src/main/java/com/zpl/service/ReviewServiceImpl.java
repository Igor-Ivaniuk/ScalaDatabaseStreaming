package com.zpl.service;

import com.zpl.entity.Review;
import com.zpl.model.ReviewDTO;
import com.zpl.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Igor Ivaniuk on 08.04.2016.
 */

@Component
public class ReviewServiceImpl implements ReviewService {

    public static final String LOREM_IPSUM_1000_WORDS = "  Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi sed nisi fermentum, rhoncus odio in, luctus felis. Vivamus ornare ipsum nec egestas porttitor. Pellentesque sollicitudin enim ut turpis pretium rutrum. Maecenas auctor, nunc in commodo feugiat, lacus risus lobortis sapien, ac scelerisque nibh ex vel nisi. Phasellus enim risus, faucibus et quam ut, pulvinar commodo erat. Phasellus urna dui, dictum ac dui et, fringilla elementum tortor. Nulla vel magna ac erat tristique hendrerit. Vestibulum non erat eget dolor tempus bibendum. Etiam ornare nunc at lacus viverra dignissim. Aliquam hendrerit hendrerit sodales. Vestibulum sed ultricies dolor, nec sodales nisl. Donec aliquet, ante eu vestibulum suscipit, est velit aliquam ligula, vel tincidunt eros risus vitae ex. Nunc gravida interdum nulla, vel lobortis nunc elementum ut.  Curabitur pulvinar arcu a rutrum placerat. Vivamus finibus nunc a mattis mattis. Nam semper at eros eget lobortis. Cras aliquet, ante vel aliquam venenatis, tortor libero sodales nibh, et pulvinar augue augue quis magna. Integer tempor sagittis leo, vitae faucibus lectus tempus nec. Mauris porttitor tellus at vehicula fringilla. Suspendisse facilisis lorem ac metus aliquet, at mattis nulla volutpat. Nulla ante quam, ultricies ut dignissim sed, imperdiet sed ligula. Aliquam faucibus cursus suscipit. Morbi venenatis, sem tempor ultricies cursus, risus purus dictum leo, quis ornare lectus dui vitae lacus. Duis rhoncus sit amet nibh sed maximus. Ut ante magna, interdum ac eleifend non, suscipit ut nisi. Pellentesque posuere dictum sapien vitae consectetur. Etiam ultricies nulla eu diam facilisis accumsan. Donec et augue elit. Integer cursus justo velit, at mollis leo finibus at.  Phasellus ut volutpat massa. Etiam pharetra porttitor tempor. Donec in interdum enim. Vivamus neque orci, blandit sed velit id, fringilla lobortis mi. Ut fringilla consectetur velit, eget facilisis velit mollis sed. Etiam viverra dictum accumsan. Maecenas consequat erat lorem, id tempus augue lobortis ac. Nunc in augue elit. Curabitur interdum arcu nisi, et tempor quam faucibus nec. Nunc rutrum vel metus eu accumsan. Nulla vitae dui blandit, viverra urna eget, semper est. Nullam nunc dolor, maximus vel metus sit amet, malesuada posuere lorem. Fusce quis augue vitae risus pulvinar lacinia ac id nisl. Ut ullamcorper mauris vitae purus commodo commodo nec nec arcu. Donec lacinia, dui nec dictum dignissim, tellus enim pulvinar lacus, sit amet condimentum quam dui sed nisi. Vivamus leo ex, ultrices eu urna eget, laoreet porttitor velit. Fusce luctus sodales nibh nec ullamcorper. Proin dignissim tempor scelerisque. Quisque vitae lectus finibus, pretium ex vitae, pharetra ante. In facilisis porta risus, a rutrum augue ullamcorper at. Duis viverra sem risus. Integer ligula tellus, hendrerit non aliquam venenatis, ullamcorper mollis diam. Etiam sed interdum ex. Suspendisse eget massa eros. Integer eget leo et erat ullamcorper pulvinar in et est. Pellentesque imperdiet suscipit sem, vitae ornare orci suscipit non. In nec diam rutrum, condimentum velit sit amet, vehicula tortor. Proin at placerat tellus. Praesent ultrices libero magna, at lacinia tortor aliquet sed. Donec in est lacus. Donec eleifend, massa et mattis blandit, tellus metus condimentum nibh, et bibendum mauris massa sed nulla. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean et justo purus. Duis vitae quam sollicitudin, lobortis quam quis, ullamcorper enim. Vestibulum consectetur sollicitudin diam, quis congue lorem placerat non. Morbi vel libero vehicula, venenatis elit vel, dapibus metus. Duis ut cursus sem. Quisque mollis blandit dui, nec consectetur mi dignissim sit amet. Aenean a accumsan enim. Quisque vitae molestie nisi, quis volutpat quam. Maecenas lorem tortor, efficitur eu tellus id, aliquam imperdiet nisl. Vestibulum velit ex, accumsan id sem ut, porttitor pharetra tellus. Fusce bibendum dolor a massa accumsan, in pretium massa pharetra. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec fringilla turpis est, id elementum ante imperdiet vehicula. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In sed leo eu felis mollis faucibus eget euismod lectus. Nunc et feugiat nunc. Praesent malesuada at ex id maximus. Quisque sed libero id elit interdum auctor vitae sed justo. Sed auctor tempor tellus iaculis volutpat. Maecenas ipsum lectus, eleifend eu ornare nec, scelerisque in justo. Morbi porta aliquet purus. Pellentesque luctus sollicitudin ex, in aliquam sapien egestas non. Praesent nulla nisi, lobortis iaculis est sit amet, maximus congue velit. Quisque volutpat erat non nunc ultricies, ut vulputate sapien sagittis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nunc nisl nunc, pharetra eget tristique ut, vehicula vitae sapien. Mauris mollis accumsan leo a faucibus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec gravida iaculis neque, ac fringilla odio consequat eu. Donec suscipit nibh odio, vel auctor tortor mollis non. Phasellus lobortis, libero non lobortis fermentum, arcu risus tempor metus, non mollis elit risus quis velit. Phasellus cursus et tellus eget luctus. Aenean mattis dolor eget erat ultricies, vitae suscipit arcu euismod. Vivamus lacinia ipsum sed placerat pulvinar. Morbi vestibulum ligula sit amet augue interdum fringilla. Aliquam vestibulum efficitur lectus, non venenatis est placerat nec. Morbi luctus, urna vel volutpat volutpat, ligula augue vulputate nulla, elementum feugiat nibh nisl in augue. Vivamus lacus ipsum, pellentesque dapibus accumsan eget, pellentesque ut felis. Integer pretium sapien tellus, in gravida justo laoreet nec. Aenean orci leo, fringilla at gravida non, pulvinar a lorem. Donec pellentesque eros eu dui elementum, ac pulvinar urna dictum. Sed rhoncus diam enim, vel accumsan nulla efficitur eget. Vivamus laoreet placerat nisl non porta. Cras ac feugiat neque, id ultricies urna. Phasellus varius commodo diam, nec dignissim arcu aliquam et. Curabitur sollicitudin, nisi vitae dignissim tempor, sapien erat feugiat odio, nec fringilla ipsum lectus vitae est. Maecenas id ullamcorper nisl. Nam et eros nec ante semper elementum. Nullam at erat dui. Praesent varius eget sem a sagittis. Pellentesque feugiat massa sed lacinia porttitor. Maecenas id dolor cursus, ultrices orci ac, pretium dolor. Mauris hendrerit in velit in gravida. Etiam accumsan nibh a libero euismod, sed feugiat ligula porttitor. Sed dictum, leo id sodales luctus, dolor odio rutrum lacus, a sagittis quam odio ut neque. Aliquam turpis erat, gravida in ultrices vitae, suscipit non tortor.";

    private static final List<String> words = Arrays.asList(LOREM_IPSUM_1000_WORDS.split(" "));

    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public void generateReviews(int customerId, int count) {
        List<Review> reviewList = new ArrayList<Review>();
        for (int i = 0; i < count; i++) {
            Review review = generateReview(customerId);
            reviewList.add(review);
        }
        reviewRepository.save(reviewList);
    }

    @Override
    public List<ReviewDTO> loadReviews(int customerId) {
        List<Review> reviews = reviewRepository.findByCustomerId(customerId);
        List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
        for (Review review : reviews) {
            reviewDTOs.add(toDto(review));
        }
        return reviewDTOs;
    }

    private ReviewDTO toDto(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setCustomerId(review.getCustomerId());
        dto.setRating(review.getRating());
        dto.setReviewText(review.getReviewText());
        return dto;
    }

    private String generateReviewText(Integer minLength, Integer maxlength) {
        Integer length = minLength + (int) (Math.random() * ((maxlength - minLength) + 1));
        Integer offset = (int) (Math.random() * ((words.size() - maxlength) + 1));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(words.get(offset + i)).append(" ");
        }
        return stringBuilder.toString();
    }

    private Review generateReview(int customerId) {
        Review review = new Review();
        Integer rating = 1 + (int) (Math.random() * 5);
        review.setCustomerId(customerId);
        review.setRating(rating);
        review.setReviewText(generateReviewText(40, 100));
        return review;
    }
}
