/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.googleapis.services;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethod;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.json.JsonHttpClient;
import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.util.Key;

import junit.framework.TestCase;

/**
 * Tests {@link GoogleKeyInitializer}.
 *
 * @author Yaniv Inbar
 */
@SuppressWarnings("deprecation")
public class GoogleKeyInitializerTest extends TestCase {

  @Deprecated
  public static class MyRequestOld extends JsonHttpRequest {
    @Key
    String key;

    public MyRequestOld() {
      super(new JsonHttpClient(
          new MockHttpTransport(), new JacksonFactory(), HttpTesting.SIMPLE_URL, "test/", null),
          HttpMethod.GET, "", null);
    }
  }

  @Deprecated
  public void testInitializeOld() {
    GoogleKeyInitializer key = new GoogleKeyInitializer("foo");
    MyRequestOld request = new MyRequestOld();
    assertNull(request.key);
    key.initialize(request);
    assertEquals("foo", request.key);
  }

  public static class MyClient extends AbstractGoogleClient {

    public MyClient(HttpTransport transport) {
      super(transport, null, HttpTesting.SIMPLE_URL, "test/", null);
    }
  }

  public static class MyRequest extends AbstractGoogleClientRequest<String> {
    @Key
    String key;

    protected MyRequest(MyClient client, String method, String uriTemplate,
        HttpContent content, Class<String> responseClass) {
      super(client, method, uriTemplate, content, responseClass);
    }
  }

  public void testInitialize2() {
    GoogleKeyInitializer key = new GoogleKeyInitializer("foo");
    MyClient client = new MyClient(new MockHttpTransport());
    MyRequest request = new MyRequest(client, "GET", "", null, String.class);
    assertNull(request.key);
    key.initialize(request);
    assertEquals("foo", request.key);
  }
}
