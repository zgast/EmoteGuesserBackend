package at.markus.EmoteGuesserBackend.document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("Pictures")
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
public class Pictures {
    @Field
    String URL;
    @Field
    String name;
}
