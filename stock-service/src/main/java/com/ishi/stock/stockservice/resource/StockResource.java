package com.ishi.stock.stockservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/stock")
public class StockResource {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{username}")
    public List<Quote> getStock(@PathVariable("username")final String userName){



        ResponseEntity<List<String>> quoteResponse = restTemplate.exchange("http://localhost:8301/rest/db/" + userName, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<String>>() {
                });

        List<String> quotes=quoteResponse.getBody();
        return quotes
                .stream()
                .map(quote ->{
                    Stock stock =getStockPrice(quote);
                    return new Quote(quote, stock.getQuote().getPrice());
                })
//                .map(this::getStockPrice)
                .collect(Collectors.toList());
    }

    private Stock getStockPrice(String quote) {
        try {
            return   YahooFinance.get(quote);
        } catch (IOException e) {
            e.printStackTrace();
            return  new Stock(quote);
        }
    }

    private class Quote {
        private  String quote;
        private BigDecimal price;

        public Quote(String quote, BigDecimal price) {
            this.quote=quote;
            this.price=price;
        }

        public String getQuote() {
            return quote;
        }
       @Autowired
        public void setQuote(String quote) {
            this.quote = quote;
        }

        public BigDecimal getPrice() {
            return price;
        }
      @Autowired
        public void setPrice(BigDecimal price) {
            this.price = price;
        }



    }
}

