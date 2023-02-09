# StructureApi

All URIs are relative to *http://psc-dam-api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getIds**](StructureApi.md#getIds) | **GET** /ids/get/{structureTechnicalId} | get ids endpoint

<a name="getIds"></a>
# **getIds**
> StructureIds getIds(structureTechnicalId)

get ids endpoint

return an StructureIds Object for the TechnicalStructureId extracted from UserInfo

### Example
```java
// Import classes:
//import fr.ans.psc.api.client.structure.reader.invoker.ApiException;
//import fr.ans.psc.api.client.structure.reader.StructureApi;


StructureApi apiInstance = new StructureApi();
String structureTechnicalId = "structureTechnicalId_example"; // String | Structure Technical ID
try {
    StructureIds result = apiInstance.getIds(structureTechnicalId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling StructureApi#getIds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **structureTechnicalId** | **String**| Structure Technical ID |

### Return type

[**StructureIds**](StructureIds.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

