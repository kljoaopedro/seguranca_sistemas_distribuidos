package security;

import java.util.Base64;
import java.util.UUID;

import static security.SecurityConstants.ERROR;
import static security.SecurityConstants.KEY;

public class Security {


    public String encripty(String message) {

        String encoded = prepare(message);

        String random = UUID.randomUUID().toString();
        String[] uuidSplit = random.split("-");


        return buildHash(uuidSplit[0], encoded, uuidSplit[4]);
    }

    public String decode(String hash) throws Exception {

        if (validateHash(hash)) {
            String encoded = hash.split(KEY)[1];
            return new String(Base64.getDecoder().decode(encoded.getBytes()));
        }

        throw new Exception(ERROR);
    }

    private boolean validateHash(String hash) {
        return null != hash && !hash.isEmpty() && hash.contains(KEY);
    }

    private String buildHash(String start, String message, String end) {
        return start + message + end;
    }

    private String prepare(String message) {
        String encoded = Base64.getEncoder().encodeToString(message.getBytes());
        return KEY.concat(encoded).concat(KEY);
    }

}
