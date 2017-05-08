package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Transaction;
import models.Book;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.stream.Collectors;


/**
 * Created by Lauris on 08/05/2017.
 */
public class BookController extends Controller {

    private FormFactory formFactory;

    @Inject
    public BookController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result books() {
        return ok(Json.toJson(Book.find.all()));
    }

    public Result book(Long id) {
        Book requestedBook = Book.find.byId(id);
        if(requestedBook != null) {
            return ok(Json.toJson(requestedBook));
        }
        return notFound();
    }

    public Result create() {
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()) {
            return badRequest(bookForm.errorsAsJson());
        }
        bookForm.get().save();
        return ok();
    }

    public Result update(Long id) {
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()) {
            return badRequest(bookForm.errorsAsJson());
        }

        Transaction txn = Ebean.beginTransaction();
        try {
            Book savedBook = Book.find.byId(id);
            if (savedBook != null) {
                Book newBookData = bookForm.get();
                savedBook.author = newBookData.author;
                savedBook.title = newBookData.title;

                savedBook.update();
                txn.commit();
            }
        } finally {
            txn.end();
        }

        return ok();
    }

    public Result delete(Long id) {
        Book.find.ref(id).delete();
        return ok();
    }
}
