package finalproject;

/**
 * @author Laken
 * @date 2025-12-06
 * @description Movie Rental Management System
 * create a UML Diagram
 */
public class RentalAccountUML {
    public static void main(String[] args) {

    }


}
//RentalAccount
//- _storeName: String
//- accountID: int
//- firstName: String
//- lastName: String
//- balanceOwed: double
//- movieIDs: int[5]
//- movieTitles: String[5]

//+ getAccountID(): int
//+ getFirstName(): String
//+ getLastName(): String
//+ getBalanceOwed(): double
//+ getMovieIDs(): int[5]
//+ getMovieTitles(): String[5]

//+ setAccountID(int accountID): void
//+ setFirstName(String name): void
//+ setLastName(String name): void

//- setMovieIDByIndex(int index, int newMovieID): void
//- setMovieTitleByIndex(int index, String newMovieTitle): void

//+ getFullName(): String    // lastName, firstName
//+ printRentedMovies(): void // movieID: movieTitle
//+ dropMovieByID(int idToDrop): void    // remove certain movie by ID
//+ rentMovie(int index, int movieID, String title): void
//- updateBalance(): void  // number of rented movies × 3.99. Must be called at the end of both rentMovie and dropMovieByID

