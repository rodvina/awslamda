package awslamda;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.ServiceException;

public class Sample {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    /*
	    Function names appear as arn:aws:lambda:us-west-2:335556330391:function:HelloFunction
	    you can retrieve the value by looking at the function in the AWS Console
	    */
	    String functionName = args[0];
	    String accessKey = args[1];
	    String secretKey = args[2];

	    InvokeResponse res = null ;
	    try {
	    	
	        Region region = Region.US_EAST_1;
	        AwsCredentials creds = AwsBasicCredentials.create(accessKey, secretKey);
	        
	        LambdaClient awsLambda = LambdaClient.builder()
	        										.region(region)
	        										.credentialsProvider(StaticCredentialsProvider.create(creds))
	        										.build();

	        //Need a SdkBytes instance for the payload
	        SdkBytes payload = SdkBytes.fromUtf8String("{\n" +
	                " \"Hello \": \"Paris\",\n" +
	                " \"countryCode\": \"FR\"\n" +
	                "}" ) ;

	        //Setup an InvokeRequest
	        InvokeRequest request = InvokeRequest.builder()
	                .functionName(functionName)
	                .payload(payload)
	                .build();

	        //Invoke the Lambda function
	        res = awsLambda.invoke(request);

	        //Get the response
	        String value = res.payload().asUtf8String() ;

	        //write out the response
	        System.out.println(value);

	    } catch(ServiceException e) {
	        e.getStackTrace();
	    }
	}

}
