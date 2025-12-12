package com.example.healifyapp

import io.restassured.RestAssured.*
import io.restassured.http.ContentType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.lessThan
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class ApiRestAssuredTest {

    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        // 1) MockWebServer başlatılıyor
        server = MockWebServer()
        server.start()

        // 2) RestAssured'in base URL'sini mock sunucuya yönlendiriyoruz
        baseURI = server.url("/").toString()

        enableLoggingOfRequestAndResponseIfValidationFails()

        System.out.println("LOG: MockWebServer başlatıldı → $baseURI")
    }

    @After
    fun teardown() {
        server.shutdown()

        System.out.println("LOG: MockWebServer kapatıldı")
    }

    @Test
    fun testGetHello() {
        // 4) GET isteğine karşılık dönecek cevap
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("Hello World")
        )

        System.out.println("LOG: GET /hello için 200 ve 'Hello World' cevabı kuyruğa alındı")

        // 5) Rest Assured GET isteği
        given()
            .log().all()
            .`when`()
            .get("hello")
            .then()
            .log().all()
            .statusCode(200)           // Statü kodu
            .body(equalTo("Hello World")) // Dönen body

        System.out.println("LOG: GET /hello testi başarıyla geçti")

        val recordedRequest = server.takeRequest()
        System.out.println("MOCK INFO: Gerçekleşen istek yolu: ${recordedRequest.path}")
    }

    @Test
    fun testPostData() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("OK")
        )

        System.out.println("LOG: POST /send için 200 ve 'OK' cevabı kuyruğa alındı")

        // 7) POST isteğine gönderilecek JSON body
        val body = mapOf("name" to "Gül")

        System.out.println("LOG: POST isteğine gönderilen body → $body")

        // 8) Rest Assured POST isteği atıyor ve sonucu doğruluyor
        given()
            .contentType(ContentType.JSON)
            .body(body)
            .log().all()
            .`when`()
            .post("send")
            .then()
            .log().all()
            .statusCode(200)        // Statü
            .body(equalTo("OK"))    // Body
            .time(lessThan(100L)) // Zaman


        System.out.println("LOG: POST /send testi başarıyla geçti")

        val recordedRequest = server.takeRequest()
        System.out.println("MOCK INFO: Gerçekleşen istek gövdesi: ${recordedRequest.body.readUtf8()}")
    }
}