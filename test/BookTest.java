import models.Book;
import models.Page;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Created by Lauris on 08/05/2017.
 */
public class BookTest {

    @Test
    public void findById() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                List<Page> pages = Page.find.all();
                assertNotNull(pages);
            }
        });
    }
}
