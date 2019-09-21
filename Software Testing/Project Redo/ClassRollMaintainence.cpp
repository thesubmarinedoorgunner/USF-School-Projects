
// ClassRollMaintainence.cpp
// purpose is to maintain class information about students in a text file
// Howard Cheung

// note: at some point I would like to separate these functions into different files
// for now, I will do it the same way as I submitted it

/**********************
Last Revision: 20190920
**********************/

#include <iostream>
#include <string>
#include <vector>
#include <map>

// might not need these
// depending on if I do admin privilege
#include <ctime>
#include <time.h>


class Student
{
public:
    Student();
    ~Student();
private:
std::string fullName;
std::string firstName;
std::string lastName;

int projectGrade;
int termGrade;
int finalGrade;
protected:
};

class ClassRollMaintainence
{
public:
    ClassRollMaintainence();
    ~ClassRollMaintainence();
    
};


int main(int argc, char const *argv[])
{
    for (;;)
    {
        break;
    }

    std::cout << "End Program\n";
    return 0;
}