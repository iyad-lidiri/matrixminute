import jakarta.persistence.*;

@Entity
@Table(
        name = "attempts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "date"}),
        indexes = {
                @Index(name = "idx_user_date", columnList = "userId,date")
        }
)
public class AttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String userId;

    public String date;

    public int attemptsUsed;

    public boolean solved;
}