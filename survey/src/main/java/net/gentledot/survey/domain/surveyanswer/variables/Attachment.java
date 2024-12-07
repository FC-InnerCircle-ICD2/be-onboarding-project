package net.gentledot.survey.domain.surveyanswer.variables;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Attachment {
    private String fileName;
    private String mediaType;
    private String path;

    private Attachment(String fileName, String mediaType, String path) {
        this.fileName = fileName;
        this.mediaType = mediaType;
        this.path = path;
    }

    public static Attachment of(String fileName, String mediaType, String path) {
        return new Attachment(fileName, mediaType, path);
    }
}
