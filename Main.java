package org.example;
import com.azure.identity.AuthorizationCodeCredential;
import com.azure.identity.AuthorizationCodeCredentialBuilder;
import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.*;

import okhttp3.Request;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.

        final String clientId = "6dffb3e6-85e6-49b4-b663-bf896d46c81f";
        final String tenantId = "de29a90e-98e1-4ce0-a3b8-f9f38f3fd6d6"; // or "common" for multi-tenant apps
        final String userName = "program@rrorbit.in";
        final String password = "password*900";
        final List<String> scopes = Arrays.asList("User.Read");

        final UsernamePasswordCredential credential = new UsernamePasswordCredentialBuilder()
                .clientId(clientId).tenantId(tenantId).username(userName).password(password)
                .build();

        if (null == scopes || null == credential) {
            throw new Exception("Unexpected error");
        }
        final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                scopes, credential);

        final GraphServiceClient<Request> graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider).buildClient();

        // GET https://graph.microsoft.com/v1.0/me?$select=displayName,jobTitle
        final User user = graphClient.me().buildRequest().select("userPrincipalName,displayName")
                .get();

        System.out.println(user.userPrincipalName);
        System.out.println(user.displayName);

    }
}