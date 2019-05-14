package com.pitang.projectPitang.utils.specifications;

import com.pitang.projectPitang.models.Movie;
import com.sun.xml.internal.ws.encoding.RootOnlyCodec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MovieSpecification {

  public static Specification<Movie> searchMovie(String title, String release_date, String original_language){

    return new Specification<Movie>() {
      @Override
      public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate queryResult = null;
        if(title!=null && release_date!= null && original_language!=null){
          queryResult = cb.and(cb.like(cb.upper(root.get("title")),"%"+title.toUpperCase()+"%"), cb.like(root.get("original_language"), original_language),
            cb.like(root.get("release_date"),  release_date + "%"));
          return queryResult;
        } else if(title!=null && release_date!=null){
         queryResult = cb.and(cb.like(cb.upper(root.get("title")),"%"+title.toUpperCase()+"%"),
            cb.like(root.get("release_date"),  release_date + "%"));
          return queryResult;
        } else if(title!=null && original_language!=null){
           queryResult = cb.and(cb.like(cb.upper(root.get("title")),"%"+title.toUpperCase()+"%"), cb.like(root.get("original_language"), original_language));
          return queryResult;
        } else if(original_language!= null && release_date!=null){
          queryResult = cb.and(cb.like(root.get("original_language"), original_language),cb.like(root.get("release_date"),  release_date + "%"));
          return queryResult;
        }else if(release_date!= null){
          queryResult = cb.and(cb.like(root.get("release_date"),  release_date + "%"));
          return queryResult;
        }else if(title != null){
          queryResult = cb.and(cb.like(cb.upper(root.get("title")),"%"+title.toUpperCase()+"%"));
          return queryResult;
        }else if(original_language!=null){
          queryResult =  cb.and(cb.like(root.get("original_language"), original_language));
          return queryResult;
        }

        return  queryResult;
      }
    };

  }
}
