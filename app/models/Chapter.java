package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Lauris on 08/05/2017.
 */
@Entity
public class Chapter extends com.avaje.ebean.Model {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="chapter_seq")
    public Long id;

    public String title;

    public Integer chapNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    public Book book;

    public Chapter() { }

    public Chapter(Long id) {
        this.id = id;
    }

    /**
     * Generic query helper for entity Chapter with id Long
     */
    public static Find<Long,Chapter> find = new Find<Long,Chapter>(){};
}
