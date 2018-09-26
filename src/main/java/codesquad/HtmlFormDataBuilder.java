package codesquad;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

public class HtmlFormDataBuilder {
    private HttpHeaders headers;
    private MultiValueMap<String, Object> params;

    private HtmlFormDataBuilder(HttpHeaders headers) {
        this.headers = headers;
        params = new LinkedMultiValueMap<>();
    }

    // ...

    public static HtmlFormDataBuilder urlEncodedForm() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HtmlFormDataBuilder(headers);
    }

    public HtmlFormDataBuilder addParameter(String key, Object value) {
        params.add(key, value);
        return this;
    }

    public HttpEntity<MultiValueMap<String, Object>> build() {
        return new HttpEntity<>(params, headers);
    }
}