# GetFullDamsApi

All URIs are relative to *http://psc-dam-reader-api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**lectureDesDAMs**](GetFullDamsApi.md#lectureDesDAMs) | **GET** /dams/get_dams/{nationalId} | Lecture des DAMs d&#x27;un PS

<a name="lectureDesDAMs"></a>
# **lectureDesDAMs**
> SimpleDams lectureDesDAMs(nationalId)

Lecture des DAMs d&#x27;un PS

Renvoie la liste des DAMs d&#x27;un PS (nationaId)

### Example
```java
// Import classes:
//import fr.ans.psc.api.client.dam.reader.invoker.ApiException;
//import fr.ans.psc.api.client.dam.reader.GetFullDamsApi;


GetFullDamsApi apiInstance = new GetFullDamsApi();
String nationalId = "nationalId_example"; // String | Identifiant National du PS
try {
    SimpleDams result = apiInstance.lectureDesDAMs(nationalId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling GetFullDamsApi#lectureDesDAMs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **nationalId** | **String**| Identifiant National du PS |

### Return type

[**SimpleDams**](SimpleDams.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

