package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable;
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remarks can take any values";

    public final String value;

    /**
     * Constructs an {@code Remark}.
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || ((other instanceof Remark && value.equals(((Remark) other).value)));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
