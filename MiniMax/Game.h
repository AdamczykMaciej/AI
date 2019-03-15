#include <iostream>
#include <vector>

using namespace std;

struct Node
{
  static vector<Node*> nodes;
  int noOfNalesniki;
  int smakowitosc;
  int score;
  bool isMaxPlayer;
  vector<Node*> children;

  Node();

  static void printNodes();
};

struct MiniMax
{

public:
  Node *root;

  MiniMax();
  void constructTree(vector<int> nalesniki_n, vector<int> nalesniki_m);
  void evaluateScore(Node *parentNode, bool Bajtek, bool flag, int numOfIterations);
  int miniMax(Node* current, int depth, bool maximizingPlayer);

private:
  void addLeaf(Node *parentNode, vector<int> nalesniki_n, vector<int> nalesniki_m);
};