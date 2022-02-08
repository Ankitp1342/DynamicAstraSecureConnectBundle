package test;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.net.URL;
import java.nio.file.Paths;

public class TestConnection {

//https://datastax-cluster-config-prod.s3.us-east-2.amazonaws.com/ca91bcb3-1fe4-4fd4-abd1-33deca9a88ab/secure-connect-enterprise.zip?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIA2AIQRQ76TUCOHUQ4%2F20220208%2Fus-east-2%2Fs3%2Faws4_request&X-Amz-Date=20220208T035626Z&X-Amz-Expires=300&X-Amz-SignedHeaders=host&X-Amz-Signature=a993c5f6ecc3d001347b0e6ea0849b783aea3bf83f4eac8a0ed04c1af4fbaa24
    public static void main(String[] args) {
        // Create the CqlSession object:

        try {
            try (CqlSession session = CqlSession.builder()
                    .withCloudSecureConnectBundle(new URL(AstraGetAuthToken.secureBundleURL()))
                    .withAuthCredentials("rinOZHyPynwWWEAxJdhwSEFj", "39evTdyYlN3rofQraacrFxJMj+MtB19KQpiSf,CH38N9SbLDdSK1NZbI32OZag31P-FsB.T_h6EKckMHj8wHAZuC7H1ZU3BT-gsrC72wtCd9mAwkOqvGodB5lTYz3vt3")
                    .build()) {
                // Select the release_version from the system.local table:
                ResultSet rs = session.execute("select release_version from system.local");
                Row row = rs.one();
                //Print the results of the CQL query to the console:
                if (row != null) {
                    System.out.println("Release Version" + row.getString("release_version"));
                } else {
                    System.out.println("An error occurred.");
                }
            }
            System.exit(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
