
// Team_Jefferson_NoFault.cpp
// Cody Rubel, Alfredo Aparicio, Howard Cheung
// implements a small class-roll maintenance system

/*******************************************************************
 * Notes:
 * Program tries to read in from a file called "students.txt" on execution into a vector 
 * If file does not exist, one will be created on normal program exit (not through ctrl+c) in the same directory
 * If you tamper with the text file and it is not in the same format for the read in, the student will be deleted upon normal termination
 * (i.e you change a grade that is out of the range of 0-4, read in will skip the creation of student and writes back only what is in vector)
 * main() is all the way at the bottom
*******************************************************************/

#include <iostream>
#include <cstdlib>
#include <vector>
#include <iterator>
#include <string>

// input/output
#include <istream>
#include <ostream>
#include <fstream>
#include <sstream>
#include <cassert>

// hash set and hash map
#include <unordered_set>
#include <unordered_map>

// for use of to_upper and to_lower
#include <locale>

// for input validation
#include <limits>

// declarations for out of order compiling
class Student;
Student studentSearch(std::vector<Student> &students, std::string search);
void addStudent(std::vector<Student> &students);
void removeStudent(std::vector<Student> &students);
void updateStudentInfo(std::vector<Student> &students);


class Student
{
    public:

    // default constructor sets grades to -1 for validation functionality
    Student()
    {
        this->firstName = "";
        this->lastName = "";
        this->UID = "";
        this->email = "";
        this->presGrade = this->essayGrade = this->termProjGrade = -1;
    }

    // sets the fields for a student with valid inputs
    Student(std::string firstName, std::string lastName, std::string UID, std::string email, std::string presGrade, std::string essayGrade, std::string termProjGrade)
    {
        this->firstName = firstName;
        this->lastName = lastName;
        this->UID = UID;
        this->email = email;
        this->presGrade = stoi(presGrade);
        this->essayGrade = stoi(essayGrade);
        this->termProjGrade = stoi(termProjGrade);
    }

    // getters and setters (for each field)
    std::string getStudentName()
    {
        return this->firstName + " " + this->lastName;
    }
    void setStudentName(std::string firstName, std::string lastName)
    {
        this->firstName = firstName;
        this->lastName = lastName;
    }

    std::string getStudentFirstName()
    {
        return this->firstName;
    }
    std::string getStudentLastName()
    {
        return this->lastName;
    }
    
    void setStudentFirstName(std::string firstName)
    {
        this->firstName = firstName;
    }
    void setStudentLastName(std::string lastName)
    {
        this->lastName = lastName;
    }

    std::string getUID()
    {
        return this->UID;
    }
    void setUID(std::string UID)
    {
        this->UID = UID;
    }
    std::string getEmail()
    {
        return this->email;
    }
    void setEmail(std::string email)
    {
        this->email = email;
    }
    int getPresGrade()
    {
        return this->presGrade;
    }
    void setPresGrade(std::string presGrade)
    {
        this->presGrade = stoi(presGrade);
    }
    int getEssayGrade()
    {
        return this->essayGrade;
    }
    void setEssayGrade(std::string essayGrade)
    {
        this->essayGrade = stoi(essayGrade);
    }
    int getTermProjGrade()
    {
        return this->termProjGrade;
    }
    void setTermProjGrade(std::string termProjGrade)
    {
        this->termProjGrade = stoi(termProjGrade);
    }

    private: 

        std::string firstName, lastName;
        std::string UID;
        std::string email;
        int presGrade;
        int essayGrade;
        int termProjGrade;        

};

// functions that read and write student information from and to file
std::vector<Student> studentFileRead()
{
    std::vector<Student> students;
    std::vector<std::string> createStudent;
    std::string get;

    std::ifstream inFS;

    inFS.open("students.txt");
    
    // returns a vector of empty students if there is no file to read from. Sorry, figure it out
    if (!inFS.is_open() )
    {
        inFS.close();
        return students;
    }

    while (inFS.good() )
    {
        getline(inFS, get);

        std::istringstream inSS(get);
        std::string temp;

        while (inSS >> temp)
        {
            createStudent.push_back(temp);
        }

        if (createStudent.size() != 7)
        {
            createStudent.clear();
            continue;
        }

        int x, y, z;
        try
        {
            if ( (createStudent.at(0).length() + createStudent.at(1).length() > 40) || createStudent.at(3).length() > 40 || createStudent.at(2).length() != 10)
            {
                continue;
            }

            x = stoi(createStudent.at(4) );
            y = stoi(createStudent.at(5) );
            z = stoi(createStudent.at(6) );
        }
        catch(...)
        {
            createStudent.clear();
            continue;
        }

        if (x < 0 || x > 4 || y < 0 || y > 4 || z < 0 || z > 4)
        {
            createStudent.clear();
            continue;
        }

        students.push_back( Student(createStudent.at(0), createStudent.at(1), createStudent.at(2), createStudent.at(3), std::to_string(x), std::to_string(y), std::to_string(z) ));
        createStudent.clear();
    }

    return students;
}

// writes all of the students from the vector to the file
// truncs the text file first so if you tampered with the file or didn't edit it correctly through here, your loss
void studentFileWrite(std::vector<Student> students)
{
    std::ofstream outFS;
    outFS.open("students.txt", std::ofstream::out | std::ofstream::trunc);

    int i = 0;
    for (Student temp : students)
    {
        outFS << temp.getStudentFirstName() << " " << temp.getStudentLastName() 
        << " " << temp.getUID() << " " << temp.getEmail() << " "
        << temp.getPresGrade() << " " << temp.getEssayGrade() << " "
        << temp.getTermProjGrade();

        if (i != students.size() - 1)
        {
            outFS << "\n";
        }
        i++;
    }

    outFS.close();
}

// generic function that asks if user wants to try specific menu option again
// after input validation, returns true if yes, false if no
bool tryAgainPrompt()
{
    char input;

    do
    {
        std::cout << "Would you like to try again ? (Y/N): ";
        std::cin >> input;

        if (std::cin.fail() || !(toupper(input) == 'Y' || toupper(input) == 'N') )
        {    
            std::cin.clear();
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }    
    }
    while (std::cin.fail() || !(toupper(input) == 'Y' || toupper(input) == 'N') );

    if (toupper(input) == 'Y')
    {
        return true;
    }
    else
    {
        return false;
    }
}

// the best blanket validation input statement I could come up with
// checks if the input the user entered is within the range passed
// returns false if not in range or if user entered a non-int
bool integerInputValidation(int min, int max, std::string input)
{
    int x;
    try
    {
        x = stoi(input); 
    }
    catch(...)
    {
        return false;
    }

    if ( !(x <= max && x >= min) )
    {
        return false;
    }

    return true;
}

// secondary menu option from main menu option 2
void updateStudent(std::vector<Student> &students)
{
    int selected;
    for(;;)
    {
        std::cout << "Update Student Menu - Please select an option: \n";
        std::cout << "1. Add a student. \n";
        std::cout << "2. Remove a student. \n";
        std::cout << "3. Update a student's information. \n";
        std::cout << "4. Go back to the main menu. \n";

        std::cin >> selected;

        while (std::cin.fail() || !(selected < 5 && selected > 0) )
        {
            // clears cin buffer
            std::cin.clear();
            // discards all input in the input stream up to and including the first newline
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            std::cout << "Invalid menu option. Please select a valid menu option. Type \"4\" to go back to the main menu. \n";
            std::cin >> selected;
        }

        if (selected == 1)
        {
            addStudent(students);
        }
        if (selected == 2)
        {
            removeStudent(students);
        }
        if (selected == 3)
        {
            updateStudentInfo(students);
        }
        if (selected == 4)
        {
            return;
        }
    }
}

// adds a student with a validation on name, email, and ID to make sure you don't add a duplicate
void addStudent(std::vector<Student> &students)
{
    std::string firstName = "", lastName = "", UID = "", email = "", presGrade = "", essayGrade = "", termProjGrade = "";
    while (1)
    {
        std::cout << "Enter the first name of student to add: ";
        std::cin >> firstName;
        std::cout << "Enter the last name of student: ";
        std::cin >> lastName;

        if (firstName.length() + lastName.length() < 41)
        {
            break;
        }
        firstName.clear();
        lastName.clear();
    }
    while (1)
    {
        std::cout << "Enter their USF ID (format: Uxxxx-xxxx): ";
        std::cin >> UID;

        if (UID.length() == 10)
        {
            break;
        }
        UID.clear();
    }

    while (1)
    {
        std::cout << "Enter their email: ";
        std::cin >> email;

        if (email.length() < 41)
        {
            break;
        }
        email.clear();
    }

    // validation so you don't add the same person 
    for (int i = 0; i < students.size(); i++)
    {
        Student temp = students.at(i);
        std::string fullName = firstName + " " + lastName;
        if (temp.getStudentName() == fullName || temp.getUID() == UID || temp.getEmail() == email)
        {
            std::cout << "Student already exists. \n";
            return;
        }
    }

    do
    {
        std::cout << "Enter their presentation grade (0-4): ";
        std::cin >> presGrade;

        if (bool repeat = integerInputValidation(0, 4, presGrade) )
        {
            break;
        }

        presGrade.clear();
    }
    while (1);

    do
    {
        std::cout << "Enter their essay grade (0-4): ";
        std::cin >> essayGrade;

        if (bool repeat = integerInputValidation(0, 4, essayGrade) )
        {
            break;
        }
        essayGrade.clear();
    } 
    while (1);

    do
    {
        std::cout << "Enter their project grade(0-4): ";
        std::cin >> termProjGrade;

        if (bool repeat = integerInputValidation(0, 4, termProjGrade) )
        {
            break;
        }
        termProjGrade.clear();
    } 
    while (1);

    // after validating grade inputs;

    Student newStudent = Student(firstName, lastName, UID, email, presGrade, essayGrade, termProjGrade);
    students.push_back(newStudent);

    std::cout << "Student successfully added! \n";

}

// removes a student if the student exists
void removeStudent(std::vector<Student> &students)
{
    Student deleteStudent;
    std::string input;
    bool repeat = false;

    do 
    {
        std::cout << "Enter the name, UID, or email of the student you would like to remove: ";
        //std::cin >> input;
    
        input.clear();
        std::cin.clear();

        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        std::getline(std::cin, input);

        deleteStudent = studentSearch(students, input);

        if (deleteStudent.getEssayGrade() == -1)
        {
            std::cout << "Student cannot be found. ";
            repeat = tryAgainPrompt();

            // if they don't want to retry finding the student, their loss
            if (!repeat)
            {
                return;
            }
        }
        // else the student is found. Use this as a bug
        else
        {
            break;
        }
    }

    while (repeat);

    for (int i = 0; i < students.size(); i++)
    {
        Student temp = students.at(i);

        if (temp.getStudentName() == deleteStudent.getStudentName() )
        {
            students.erase(students.begin() + i);
        }
    }

    std::cout << "Student deleted. \n";
}

// function updates a student if the student exists. Has input validation as well
void updateStudentInfo(std::vector<Student> &students)
{
    Student student;
    std::string search;
    bool repeat = true;

    // once passed validation
    int selection;
    std::string input;

    do
    {
        std::cout << "Enter the name, USF ID, or email of the student: ";
        search.clear();
        std::cin.clear();
        
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        std::getline(std::cin, search);
        
        student = studentSearch(students, search);

        if (student.getEssayGrade() == -1)
        {
            std::cout << "Student not found. ";
            repeat = tryAgainPrompt();

            // if user does not want to try this function again
            if ( !repeat )
            {
                return;
            } 
        }
        // else the student is found
        else
        {
            break;
        }
    }
    while (repeat);

    std::cout << "What would you like to update? \n"
    << "1. Name \n"
    << "2. USF ID \n"
    << "3. Email \n"
    << "4. Presentation Grade \n"
    << "5. Essay Grade \n"
    << "6. Term Project Grade \n"
    << "7. N/A (return to menu) \n";

    std::cin >> selection;

    while (std::cin.fail() || !(selection < 8 && selection > 0) )
    {
        std::cin.clear();
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

        std::cout << "Invalid option. Please try again. Press \"7\" to return to menu. ";
        std::cin >> selection; 
    }

    if (selection == 7)
    {
        return;
    }

    std::cout << "Old ";
    switch (selection)
    {
        case 1:
            std::cout << "name is: "; 
            std::cout << student.getStudentName();
            break;
        case 2:
            std::cout << "USF ID is: "; 
            std::cout << student.getUID();
            break;
        case 3:
            std::cout << "email is: "; 
            std::cout << student.getEmail();
            break;
        case 4:
            std::cout << "presentation grade is: "; 
            std::cout << student.getPresGrade();
            break;
        case 5:
            std::cout << "essay grade is: "; 
            std::cout << student.getEssayGrade();
            break;
        case 6:
            std::cout << "term project grade is: "; 
            std::cout << student.getTermProjGrade();
            break;
    }

    std::cout << "\nEnter their new ";
    switch (selection)
    {
        case 1: 
            std::cout << "name: ";
            break;
        case 2: 
            std::cout << "USF ID (format: Uxxxx-xxxx): ";
            break;
        case 3:
            std::cout << "email: ";
            break;
        case 4: 
            std::cout << "presentation grade: ";
            break;
        case 5: 
            std::cout << "essay grade: ";
            break;
        case 6: 
            std::cout << "term project grade: ";
            break;
    }

    std::string firstName;
    std::string lastName;
    if (selection == 1)
    {
        while (1)
        {
            std::cout << "\nFirst name: ";
            std::cin >> firstName;
            std::cout << "Last name: ";
            std::cin >> lastName;

            if (firstName.length() + lastName.length() < 41)
            {
                break;
            }
            firstName.clear();
            lastName.clear();
        }
    }
    else 
    {
        std::cin >> input;
    }
    if (selection == 2)
    {
        while (1)
        {
            if (input.length() == 10)
            {
                break;
            }

            input.clear();
            std::cout << "Enter their new USD ID (format: Uxxxx-xxxx): ";
            std::cin >> input;
        }
    }
    if (selection == 3)
    {
        while (1)
        {
            if (input.length() < 41)
            {
                break;
            }

            input.clear();
            std::cout << "Enter their new email: ";
            std::cin >> input;
        }
    }
    if (selection > 3 && selection < 7)
    {
        do
        {
            if (bool repeat = integerInputValidation(0, 4, input) )
            {
                break;
            }
            input.clear();
            std::cout << "Enter a number between 0 and 4: ";
            std::cin >> input;
        }
        while (1);
    }

    for (int i = 0; i < students.size(); i++)
    {
        if (students.at(i).getStudentName() == student.getStudentName() )
        {
            switch (selection)
            {
                case 1:
                {
                    students.at(i).setStudentName(firstName, lastName);
                    break;
                }    
                case 2:
                    students.at(i).setUID(input);
                    break;
                case 3: 
                    students.at(i).setEmail(input);
                    break;
                case 4: 
                    students.at(i).setPresGrade(input);
                    break;
                case 5:
                    students.at(i).setEssayGrade(input);
                    break;
                case 6:
                    students.at(i).setTermProjGrade(input);
            }
        }
    }

    std::cout << "Student information updated. \n";
}

// function is called in multiple menu options to validate the search for a student
// looks for the student in the vector
// if the student does not exist, then it returns a blank student with -1 grades which functions handle
Student studentSearch(std::vector<Student> &students, std::string search)
{
    Student found = Student();

    // all this to split the string from the user into first and last name
    std::istringstream iss(search);
    std::vector<std::string> name( (std::istream_iterator<std::string>(iss) ), std::istream_iterator<std::string>() );

    for (Student temp : students)
    {
        try
        {
            if(name.at(0).compare(temp.getStudentFirstName() ) == 0 && name.at(1).compare(temp.getStudentLastName() ) == 0)
            {
                return temp;
            }
        }
        catch(...){}
        if (search.compare(temp.getUID() ) == 0)
        {
            return temp;
        }
        if (search.compare(temp.getEmail() ) == 0)
        {
            return temp;
        }
    }

    // if student is not found, then the default constructor will return a blank student with -1 as their grades
    return found;
}

// menu option 1
void studentLookup(std::vector<Student> students)
{
    int selected;
    std::string info;

    Student student;

    char grades[] = {'F', 'D', 'C', 'B', 'A'};

    std::cout << "Enter the name, USF ID, or email of the student you are looking for: ";

    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    std::getline(std::cin, info);

    student = studentSearch(students, info);

    if (student.getPresGrade() == -1)
    {
        std::cout << "Student does not exist. \n";
    }
    else
    {
        std::cout << "Student found!: \n";
        std::cout << "1. Name: " << student.getStudentName() << "\n";
        std::cout << "2. USF ID: " << student.getUID() << "\n";
        std::cout << "3. Email: " << student.getEmail() << "\n";
        std::cout << "4. Presentation Grade: " << grades[student.getPresGrade()] << "\n";
        std::cout << "5. Essay Grade: " << grades[student.getEssayGrade()] << "\n";
        std::cout << "6. Term Project Grade: " << grades[student.getTermProjGrade()] << "\n";    
    }
}

// main menu function with input validation 
int menu()
{
    int selected;
    std::cout << "1. Look up student. \n";
    std::cout << "2. Update student information. \n";
    std::cout << "3. Quit. \n";

    std::cin >> selected;

    // input validation against all types of input
    while (std::cin.fail() || !(selected < 4 && selected > 0) )
    {
        // clears cin buffer
        std::cin.clear();
        // discards all input in the input stream up to and including the first newline
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        std::cout << "Invalid menu option. Please select a valid menu option. Type \"3\" to quit. \n";
        std::cin >> selected; 
    }

    return selected;
}

// main should take no arguments. I mean you can but it's not going to use them 
// main function calls menu() in infinite loop until user selects 3 or ctrl+c
// reads in from text file before and writes out to a text file before termination (will create one if one isn't there)
int main(int argc, char const *argv[])
{
    std::vector<Student> students = studentFileRead();

    int selected;
    for(;;)
    {
        std::cout << "Please select a menu option: \n";
        selected = menu();
        if (selected == 1)
        {
            studentLookup(students);
        } 
        if (selected == 2)
        {
            updateStudent(students);
        }
        if (selected == 3)
        {
            break;
        }
    }

    studentFileWrite(students);
    return 0;
}
