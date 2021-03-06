package no.nav.pus.decorator.feature;

import no.nav.sbl.featuretoggle.unleash.UnleashService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class FeatureResourceTest {

    @Test
    public void userIdFromJwt() {
        // utløpt ikke-sensitivt test-token
        String testToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6ImZ5akpfczQwN1ZqdnRzT0NZcEItRy1IUTZpYzJUeDNmXy1JT3ZqVEFqLXcifQ.eyJleHAiOjE1MjQwNTA1MjQsIm5iZiI6MTUyNDA0NjkyNCwidmVyIjoiMS4wIiwiaXNzIjoiaHR0cHM6Ly9sb2dpbi5taWNyb3NvZnRvbmxpbmUuY29tL2QzOGYyNWFhLWVhYjgtNGM1MC05ZjI4LWViZjkyYzEyNTZmMi92Mi4wLyIsInN1YiI6IjI4MDE3OTE4OTI1IiwiYXVkIjoiMDA5MGI2ZTEtZmZjYy00YzM3LWJjMjEtMDQ5ZjdkMWYwZmU1IiwiYWNyIjoiTGV2ZWw0Iiwibm9uY2UiOiJhZUF1WFc5WUNaZVJnX1NMN0VWTUJkYUtROUhlQXdCTkQ5UW8wQkYxUlQ4IiwiaWF0IjoxNTI0MDQ2OTI0LCJhdXRoX3RpbWUiOjE1MjQwNDY5MjQsImp0aSI6IjdqQnRvRzhSRThIZDFsTUw2dTc4dC1qZ0IzeXRBYnJhcmpFTG05QmdXMkU9IiwiYXRfaGFzaCI6IlRnSkN2VEQxQ1dSRUdaZEV5VjR6REEifQ.T8wfwzRTAD82Nx1yvZXOZ-FwyWBQsVcSJfzka-a1BcVtaF2bS1oBZQpc-r_eXCq4UG4uJMaGPTRoUxXh_dCwYEzPLci_I7dvTN4rSLgn-0Lzii1F6Rb6Fwt-3PWgvc8YXzW9Zzaf_UYNFdMm5nW-KqnF5nksOgTYkWv1czstNRrykecgFNK_ZGCmNBhXF5kPEjGzD_sQcsfiitswvcYzfIlTSjdCPDOEAaLC-xYyRwdm9mIc1gJI7ueq2COnsNKvc2rM1ZtfbzLS6iojGbwStk1lc6xc00feg5qQ1VwIzDJ4E1lAtp1Ek1g_5FAVQgyW-BB_TE4vLoWaro7oQz74Qg";
        assertThat(FeatureResource.userIdFromJwt(null, "invalid", testToken)).hasValue("28017918925");
    }

    @Test
    public void evaluate__acceptDubplicateKeys() {
        UnleashService unleashService = mock(UnleashService.class);
        FeatureResource featureResource = new FeatureResource(unleashService);

        Map<String, Boolean> features = featureResource.evaluate(
                Arrays.asList("a", "a", "a"),
                null,
                null,
                null,
                mock(HttpServletRequest.class),
                mock(HttpServletResponse.class)
        ).getFeatures();

        assertThat(features)
                .hasSize(1)
                .containsEntry("a", false);
    }

}