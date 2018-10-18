package no.nav.pus.decorator;

import no.nav.sbl.util.EnvironmentUtils;

import java.util.function.Function;

import static no.nav.sbl.util.EnvironmentUtils.EnviromentClass.Q;
import static no.nav.sbl.util.EnvironmentUtils.EnviromentClass.T;

public class CspService {

    public static String generateCspDirectives() {
        return ""
                + " default-src 'self' appres.nav.no" + appresTest() + " tjenester.nav.no" + tjenesterTest() + ";"
                + " script-src 'self' 'unsafe-inline' 'unsafe-eval' appres.nav.no" + appresTest() + " www.googletagmanager.com www.google-analytics.com script.hotjar.com static.hotjar.com;"
                + " img-src 'self' appres.nav.no" + appresTest() + " static.hotjar.com data: ;"
                + " style-src 'self' 'unsafe-inline' appres.nav.no" + appresTest() + ";"
                + " font-src 'self' static.hotjar.com data: ;"
                + " frame-src vars.hotjar.com video.qbrick.com;" // video i aktivitetsplan, mulig den bør ha spesialisert CSP
                + " report-uri /frontendlogger/api/warn;";
    }

    private static String appresTest() {
        return testResourceDirective(e -> String.format("appres-%s.nav.no", e));
    }

    private static String tjenesterTest() {
        return testResourceDirective(e -> String.format("tjenester-%s.nav.no", e));
    }

    private static String testResourceDirective(Function<String, String> formatter) {
        EnvironmentUtils.EnviromentClass environmentClass = EnvironmentUtils.getEnvironmentClass();
        if (environmentClass == T || environmentClass == Q) {
            return " " + formatter.apply(EnvironmentUtils.requireEnvironmentName());
        } else {
            return "";
        }
    }

}
