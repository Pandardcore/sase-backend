package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Lauris on 08/05/2017.
 */
@Entity
public class Book extends com.avaje.ebean.Model {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="book_seq")
    public Long id;

    @Constraints.Required
    public String title;

    public String author;

    public Book() { }

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    /**
     * Generic query helper for entity Book with id Long
     */
    public static Find<Long,Book> find = new Find<Long,Book>(){};
}
