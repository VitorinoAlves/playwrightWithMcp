package com.example.bdd.api.client;

import com.example.bdd.api.model.ApiResponse;
import com.example.bdd.api.model.CreateAccountRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;

public class UserApiClient {
    private static final String BASE_URL = "https://automationexercise.com/api";
    private final APIRequestContext requestContext;

    public UserApiClient(Page page) {
        this(page.request());
    }

    public UserApiClient(APIRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public APIResponse createAccount(CreateAccountRequest request) {
        return requestContext.post(
                BASE_URL + "/createAccount",
                RequestOptions.create().setForm(request.toFormData())
        );
    }

    public APIResponse deleteAccount(String email, String password) {
        FormData form = FormData.create()
                .set("email", email)
                .set("password", password != null ? password : "");
        return requestContext.delete(
                BASE_URL + "/deleteAccount",
                RequestOptions.create().setForm(form)
        );
    }

    public APIResponse getUserDetailByEmail(String email) {
        return requestContext.get(
                BASE_URL + "/getUserDetailByEmail",
                RequestOptions.create().setQueryParam("email", email)
        );
    }

    public ApiResponse parseApiResponse(APIResponse response) {
        JsonObject json = JsonParser.parseString(response.text()).getAsJsonObject();
        int responseCode = json.has("responseCode") ? json.get("responseCode").getAsInt() : response.status();
        String message = json.has("message") ? json.get("message").getAsString() : "";
        return new ApiResponse(responseCode, message);
    }
}
