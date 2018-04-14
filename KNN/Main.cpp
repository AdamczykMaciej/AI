#include <iostream>
#include <set>
#include <fstream>
#include <sstream>

using namespace std;

struct Element{
    double a, b, c, d;
};

int KKN(set<Element> cases){
    
}

int main(){
    double a, b, c, d;
		set<Element> irisset;
		//set<int>::iterator it;
    //cin>>a>>b>>c>>d; // sample input
    ifstream readFile("iris/train.txt"); // reading

    while(readFile){
        
				Element iris;
				string s;
				scanf("%f, %f, %f, %f", &a, &b, &c, &d); getline(readFile, s);
				cout<<s<<endl;
				// irisset.insert(iris);
		}
		// for(auto i: irisset){
		// 	// cout<<i.b<<endl;
		// }


}