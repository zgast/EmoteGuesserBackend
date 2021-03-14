package at.markus.EmoteGuesserBackend.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Field;

@Document("User")
@AllArgsConstructor
@Data
public class User{
    @Id
    Integer objectId;
    @Field
    String userId;
    @Field
    String name;
}