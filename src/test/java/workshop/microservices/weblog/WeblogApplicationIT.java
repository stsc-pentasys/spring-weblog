package workshop.microservices.weblog;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import workshop.microservices.weblog.resource.ArticleRequest;

public class WeblogApplicationIT extends IntegrationBase {

	@Value("${local.server.port}")
	private int serverPort;

	@Before
	public void setUpRestAssured() throws Exception {
		baseURI = "http://localhost";
		port = serverPort;
		basePath = "/articles";
	}

	@Test
	public void getArticleSuccess() throws Exception {
		given().pathParam("articleId", "is-tdd-dead")
				.when()
					.get("/{articleId}")
				.then()
					.statusCode(200)
					.contentType(ContentType.JSON)
					.body("title", equalTo("Is TDD Dead?"));
	}

	@Test
	public void getArticleFailsForUnknownId() throws Exception {
		given().pathParam("articleId", "entry-does-not-exist")
				.when()
					.get("/{articleId}")
				.then()
					.statusCode(404);
	}

	@Test
	public void getArticlesSuccess() throws Exception {
		when().get()
				.then()
					.statusCode(200)
					.contentType(ContentType.JSON)
					.body("title", hasItems("Is TDD Dead?", "Explore capabilities, not features"));
	}

	@Test
	public void postArticleSuccess() throws Exception {
		ArticleRequest request = new ArticleRequest("martin", "Brand new article", "Some heavy lifting ...");
		given().contentType(ContentType.JSON).body(request)
				.when()
					.post()
				.then()
					.statusCode(201)
					.header("Location", "http://localhost:" + port + "/articles/brand-new-article");
	}

	@Test
	public void postArticleFailsForUnknownAuthor() throws Exception {
		ArticleRequest request = new ArticleRequest("johndoe", "Boring old article", "Some heavy lifting ...");
		given().contentType(ContentType.JSON).body(request)
				.when()
					.post()
				.then()
					.statusCode(403);
	}

	@Test
	public void postArticleFailsForDuplicateTitle() throws Exception {
		ArticleRequest request = new ArticleRequest("martin", "Is TDD dead?", "Some heavy lifting ...");
		given().contentType(ContentType.JSON).body(request)
				.when()
					.post()
				.then()
					.statusCode(409);
	}

	@Test
	public void putArticleSuccess() throws Exception {
		ArticleRequest request = new ArticleRequest("martin", "Spring forever", "Who needs Guice & Co.?");
		given().pathParam("articleId", "the-dependency-injection-pattern")
				.contentType(ContentType.JSON)
				.body(request)
				.when()
					.put("/{articleId}")
				.then()
					.statusCode(200)
					.contentType(ContentType.JSON)
					.body("articleId", is("the-dependency-injection-pattern"))
					.body("title", is("Spring forever"));
	}

	@Test
	public void putArticleFailsForWrongAuthor() throws Exception {
		ArticleRequest request = new ArticleRequest("kent", "Spring forever", "Who needs Guice & Co.?");
		given().pathParam("articleId", "the-dependency-injection-pattern")
				.contentType(ContentType.JSON)
				.body(request)
				.when()
				.put("/{articleId}")
				.then()
					.statusCode(403);
	}

	@Test
	public void putArticleFailsForUnknownId() throws Exception {
		ArticleRequest request = new ArticleRequest("martin", "Spring forever", "Who needs Guice & Co.?");
		given().pathParam("articleId", "some-made-up-id")
				.contentType(ContentType.JSON)
				.body(request)
				.when()
				.put("/{articleId}")
				.then()
					.statusCode(404);
	}
}
