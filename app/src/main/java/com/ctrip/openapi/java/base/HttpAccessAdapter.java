package com.ctrip.openapi.java.base;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import com.ctrip.openapi.java.utils.ConfigData;
import com.ctrip.openapi.java.utils.SignatureUtils;

// Http访问器
public class HttpAccessAdapter {
	// Http请求静态信息
	private ArrayList<HttpRequestProperty> StaticHttpRequestProperty = LoadStaticHttpRequestProperties();

	// 发送指定的请求到指定的URL上
	public String SendRequestToUrl(String requestContent, String urlString,
								   String paraName) {
		InputStream errorStream = null;
		HttpURLConnection httpCon = null;
		try {
			URL url = new URL(urlString);
			String content = XMLToString(requestContent);
			String soapMessage = AddSoapShell(content);
			httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setRequestMethod("POST");
			httpCon.setDoOutput(true);
			httpCon.setDoInput(true);

			for (int i = 0; i < StaticHttpRequestProperty.size(); i++) {
				httpCon.setRequestProperty(StaticHttpRequestProperty.get(i)
						.getName(), StaticHttpRequestProperty.get(i).getValue());
			}
			System.out.println(soapMessage);
			httpCon.setRequestProperty("Content-Length",
					String.valueOf(soapMessage.length()));

			OutputStream outputStream = httpCon.getOutputStream();
			outputStream.write(soapMessage.getBytes("UTF-8"));
			outputStream.close();

			InputStream inputStream = httpCon.getInputStream();
			// String encoding = httpCon.getRequestProperty("Accept-Encoding");
			BufferedReader br = null;
			// httpCon.getResponseMessage();
			if (httpCon.getRequestProperty("Accept-Encoding") != null) {
				try {
					GZIPInputStream gzipStream = new GZIPInputStream(
							inputStream);
					br = new BufferedReader(new InputStreamReader(gzipStream,
							"UTF-8"));
				} catch (IOException e) {
					br = new BufferedReader(new InputStreamReader(inputStream,
							"UTF-8"));
				}
			} else {
				br = new BufferedReader(new InputStreamReader(inputStream,
						"UTF-8"));
			}
			StringBuffer result = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			return StringToXML(RemoveSoapShell(result.toString()));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			errorStream = httpCon.getErrorStream();
			if (errorStream != null) {
				String errorMessage = null;
				String line = null;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						errorStream));
				try {
					while ((line = br.readLine()) != null) {
						errorMessage += line;
					}
					return errorMessage;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			try {
				errorStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (SdkSystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpCon != null) {
				httpCon.disconnect();
			}
		}

		return "";
	}

	// 将Soap外壳添加到请求体上
	private String AddSoapShell(
	/* String parameterName, */String patameterValue) throws Exception {
		BufferedReader bufferedReader = null;
		try {
			InputStream in = HttpAccessAdapter.class
					.getResourceAsStream("/RequestSOAPTemplate.xml");
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			String text = bufferedReader.readLine();
			StringBuilder soapShellStringBuilder = new StringBuilder();
			while (text != null) {
				soapShellStringBuilder.append(text);
				text = bufferedReader.readLine();
			}
			String result = soapShellStringBuilder.toString();
			return result.replaceAll("string", patameterValue);
		} catch (FileNotFoundException e) {
			throw new SdkSystemException("无法找到请求模板文件");
		} catch (IOException e) {
			throw new SdkSystemException("无法读取请求模板文件");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// 删除Soap外壳信息
	private String RemoveSoapShell(String source) {
		String result = "";
		int indexElementBegin = source.indexOf("<RequestResult>");
		int indexElementEnd = source.indexOf("</RequestResult>");
		if (indexElementBegin > 0 && indexElementEnd > 0) {
			result = source.substring(
					indexElementBegin + "<RequestResult>".length(),
					indexElementEnd);
		}
		return result;
	}

	// 将xml文档转换为可传输的字符串
	private static String XMLToString(String source) {
		String result = source.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		return result;
	}

	// 将返回的字符串转换为可反序列化XML文本
	private String StringToXML(String source) {
		String result = source.replaceAll("&lt;", "<");
		result = result.replaceAll("&gt;", ">");
		return result;
	}

	// 加载静态信息
	private ArrayList<HttpRequestProperty> LoadStaticHttpRequestProperties() {
		ArrayList<HttpRequestProperty> staticHttpRequestProperty = new ArrayList<HttpRequestProperty>();
		// staticHttpRequestProperty.add(new HttpRequestProperty("Host",
		// "crmint.dev.sh.ctriptravel.com"));
		staticHttpRequestProperty.add(new HttpRequestProperty("Content-Type",
				"text/xml; charset=UTF-8"));
		staticHttpRequestProperty.add(new HttpRequestProperty("SOAPAction",
				"http://ctrip.com/Request"));
		staticHttpRequestProperty.add(new HttpRequestProperty(
				"Accept-Encoding", "gzip, deflate"));

		return staticHttpRequestProperty;
	}

	public static void main(String[] args) {

		// String request =
		// "<Request><Header AllianceID=\"1\" SID=\"1\" TimeStamp=\"1352265056449\" Signature=\"E7C13030A4763015A3F7BA5C32613FCF\" RequestType=\"OTA_Ping\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/><HotelRequest><RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><ns:OTA_PingRQ><ns:EchoData>联通测试</ns:EchoData></ns:OTA_PingRQ></RequestBody></HotelRequest></Request>";
		String city = "2";
		String request = new HttpAccessAdapter().createHotelRequestXml(city,
				"汉庭");
		String url = "http://openapi.ctrip.com/Hotel/OTA_HotelSearch.asmx?wsdl";
		// String url =
		// "http://crmint.dev.sh.ctriptravel.com/Hotel/OTA_Ping.asmx?wsdl";
		String paraName = "requestXML";

		HttpAccessAdapter httpAccessAdapter = new HttpAccessAdapter();
		String response = httpAccessAdapter.SendRequestToUrl(request, url,
				paraName);
		System.out.println(response);
	}

	/**
	 * 构造访问酒店的数据
	 *
	 * @param city
	 * @return
	 */
	public String createHotelRequestXml(String city, String tag) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("<Request><Header AllianceID=\"");
			sb.append(ConfigData.USER_KEY);
			sb.append("\" SID=\"");
			sb.append(ConfigData.ULR_KEY);
			sb.append("\" TimeStamp=\"");
			long timestamp = SignatureUtils.GetTimeStamp();
			sb.append(timestamp);
			sb.append("\" Signature=\"");
			String signature = SignatureUtils.CalculationSignature(timestamp
							+ "", ConfigData.USER_KEY, ConfigData.XIECHENG_KEY,
					ConfigData.ULR_KEY, ConfigData.Hotel_RequestType);
			System.out.println(signature);
			sb.append(signature);
			sb.append("\" RequestType=\"");
			sb.append(ConfigData.Hotel_RequestType);
			sb.append("\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/>");
			sb.append("<HotelRequest><RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"> "
					+ "<ns:OTA_HotelSearchRQ Version=\"0.0\" PrimaryLangID=\"zh\" xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelSearchRQ.xsd\" xmlns=\"http://www.opentravel.org/OTA/2003/05\">"
					+ "<ns:Criteria><ns:Criterion><ns:HotelRef HotelCityCode=\""
					+ city
					+ "\" /> </ns:Criterion></ns:Criteria>"
					+ "</ns:OTA_HotelSearchRQ>"
					+ "</RequestBody></HotelRequest>");
			sb.append("</Request>");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		System.err.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 构造访问酒店价格的数据
	 *
	 * @param
	 * @return
	 */
	public String createHotelPriceXml(String startTime, String endTime,
									  String hotelCode) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("<Request><Header AllianceID=\"");
			sb.append(ConfigData.USER_KEY);
			sb.append("\" SID=\"");
			sb.append(ConfigData.ULR_KEY);
			sb.append("\" TimeStamp=\"");
			long timestamp = SignatureUtils.GetTimeStamp();
			sb.append(timestamp);
			sb.append("\" Signature=\"");
			String signature = SignatureUtils.CalculationSignature(timestamp
							+ "", ConfigData.USER_KEY, ConfigData.XIECHENG_KEY,
					ConfigData.ULR_KEY, ConfigData.Hotel_DetailPrice);
			System.out.println(signature);
			sb.append(signature);
			sb.append("\" RequestType=\"");
			sb.append(ConfigData.Hotel_DetailPrice);
			sb.append("\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/>");
			sb.append("<HotelRequest><RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><ns:OTA_HotelRatePlanRQ TimeStamp=\"2012-05-01T00:00:00.000+08:00\" Version=\"1.0\">  <ns:RatePlans>    <ns:RatePlan>      <ns:DateRange Start=\""
					+ startTime
					+ "\" End=\""
					+ endTime
					+ "\"/>      <ns:RatePlanCandidates>       <ns:RatePlanCandidate AvailRatesOnlyInd=\"true\" >          <ns:HotelRefs>            <ns:HotelRef HotelCode=\""
					+ hotelCode
					+ "\"/>          </ns:HotelRefs>        </ns:RatePlanCandidate>      </ns:RatePlanCandidates>    </ns:RatePlan>  </ns:RatePlans></ns:OTA_HotelRatePlanRQ></RequestBody></HotelRequest>");
			sb.append("</Request>");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		System.err.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 构造访问酒店的数据
	 *
	 * @param
	 * @return
	 */
	public String createHotelDetailRequestXml(String id) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("<Request><Header AllianceID=\"");
			sb.append(ConfigData.USER_KEY);
			sb.append("\" SID=\"");
			sb.append(ConfigData.ULR_KEY);
			sb.append("\" TimeStamp=\"");
			long timestamp = SignatureUtils.GetTimeStamp();
			sb.append(timestamp);
			sb.append("\" Signature=\"");
			String signature = SignatureUtils.CalculationSignature(timestamp
							+ "", ConfigData.USER_KEY, ConfigData.XIECHENG_KEY,
					ConfigData.ULR_KEY, ConfigData.Hotel_DeailType);
			System.out.println(signature);
			sb.append(signature);
			sb.append("\" RequestType=\"");
			sb.append(ConfigData.Hotel_DeailType);
			sb.append("\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/>");
			sb.append("<OTA_HotelDescriptiveInfoRQ Version=\"1.0\" xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelDescriptiveInfoRQ.xsd\" xmlns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">  <HotelDescriptiveInfos><HotelDescriptiveInfo HotelCode=\""
					+ id
					+ "\">      <HotelInfo SendData=\"true\"/>      <FacilityInfo SendGuestRooms=\"true\"/>      <AreaInfo SendAttractions=\"true\" SendRecreations=\"true\"/>      <MultimediaObjects SendData=\"true\"/>    </HotelDescriptiveInfo>    <HotelDescriptiveInfo HotelCode=\""
					+ id
					+ "\">      <HotelInfo SendData=\"true\"/>      <FacilityInfo SendGuestRooms=\"true\"/>      <AreaInfo SendAttractions=\"true\" SendRecreations=\"true\"/>      <MultimediaObjects SendData=\"true\"/>    </HotelDescriptiveInfo>    <HotelDescriptiveInfo HotelCode=\""
					+ id
					+ "\">      <HotelInfo SendData=\"true\"/>      <FacilityInfo SendGuestRooms=\"true\"/>      <AreaInfo SendAttractions=\"true\" SendRecreations=\"true\"/>      <MultimediaObjects SendData=\"true\"/>    </HotelDescriptiveInfo>  </HotelDescriptiveInfos></OTA_HotelDescriptiveInfoRQ>");
			sb.append("</Request>");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		System.err.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 构造访问酒店的数据
	 *
	 * @param
	 * @return
	 */
	public String createHotelCheckRequestXml(String hotelCode,
											 String startTime, String endTime, String count, String planCode) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("<Request><Header AllianceID=\"");
			sb.append(ConfigData.USER_KEY);
			sb.append("\" SID=\"");
			sb.append(ConfigData.ULR_KEY);
			sb.append("\" TimeStamp=\"");
			long timestamp = SignatureUtils.GetTimeStamp();
			sb.append(timestamp);
			sb.append("\" Signature=\"");
			String signature = SignatureUtils.CalculationSignature(timestamp
							+ "", ConfigData.USER_KEY, ConfigData.XIECHENG_KEY,
					ConfigData.ULR_KEY, ConfigData.Hotel_CheckType);
			System.out.println(signature);
			sb.append(signature);
			sb.append("\" RequestType=\"");
			sb.append(ConfigData.Hotel_CheckType);
			sb.append("\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\"/>");
			sb.append("<HotelRequest><RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><ns:OTA_HotelAvailRQ Version=\"1.0\" TimeStamp=\"2012-04-20T00:00:00.000+08:00\">  <ns:AvailRequestSegments>    <ns:AvailRequestSegment>      <ns:HotelSearchCriteria>        <ns:Criterion>          <ns:HotelRef HotelCode=\""
					+ hotelCode
					+ "\"/>          <ns:StayDateRange Start=\""
					+ startTime
					+ "T13:00:00.000+08:00\" End=\""
					+ endTime
					+ "T15:00:00.000+08:00\"/>          <ns:RatePlanCandidates>            <ns:RatePlanCandidate RatePlanCode=\""
					+ planCode
					+ "\"/>          </ns:RatePlanCandidates>          <ns:RoomStayCandidates>            <ns:RoomStayCandidate Quantity=\"2\">              <ns:GuestCounts IsPerRoom=\"true\">                <ns:GuestCount Count=\""
					+ count
					+ "\"/>              </ns:GuestCounts>            </ns:RoomStayCandidate>          </ns:RoomStayCandidates>          <ns:TPA_Extensions>            <ns:LateArrivalTime>"
					+ endTime
					+ "T15:00:00.000+08:00</ns:LateArrivalTime>          </ns:TPA_Extensions>        </ns:Criterion>      </ns:HotelSearchCriteria>    </ns:AvailRequestSegment>  </ns:AvailRequestSegments></ns:OTA_HotelAvailRQ></RequestBody></HotelRequest>");
			sb.append("</Request>");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		System.err.println(sb.toString());
		return sb.toString();
	}

}
