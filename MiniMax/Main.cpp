#include <iostream>
#include <vector>

#include "Game.h"

using namespace std;

vector<int> fillNalesniki(vector<int> nalesniki)
{
  int nalesnik;
  for (size_t i = 0; i < nalesniki.size(); i++)
  {
    cin >> nalesnik;
    nalesniki[i] = nalesnik;
  }
  return nalesniki;
}

void printNalesniki(vector<int> nalesniki)
{
  cout << "Nalesniki: ";
  for (size_t i = 0; i < nalesniki.size(); i++)
  {
    cout << nalesniki[i] << " ";
  }
  cout << endl;
}

/*  MiniMax Algorithm, constructing a game tree, evaluation of nodes
  
  Complexity O(b^d), b - is the number of legal moves (it can vary depending on a game by examining the tree deeper)
  d- the depth of our tree

  By using ALpha-Beta Pruning it can be dimished to sth like O(b^(d/2)).
  
  Obtaining the best result, given our opponent plays optimally. We assume that we start first.

  Problem: https://sio2.staszic.waw.pl/c/algorytmy-zaawansowane-201819/p/nal/?fbclid=IwAR2oGxVuGpq58kSF5PBjpyVl60E6I-IfdHAUU4DqByi8ieCEb94i2MFJTuQ

  author: Maciej Adamczyk, 28.02.2019
*/
int main()
{
  int n, m;
  vector<int> nalesniki_n, nalesniki_m;
  cin >> n >> m;
  nalesniki_n.resize(n);
  nalesniki_m.resize(m);
  nalesniki_n = fillNalesniki(nalesniki_n);
  nalesniki_m = fillNalesniki(nalesniki_m);
  printNalesniki(nalesniki_n);
  printNalesniki(nalesniki_m);

  MiniMax *mx = new MiniMax();
  try
  {
    mx->constructTree(nalesniki_n, nalesniki_m);
  }
  catch (...)
  {
    cout << "Exception" << endl;
  }
  cout << "Leaves" << endl;
  cout << "Number of nodes: " << Node::nodes.size() << endl;

  mx->evaluateScore(mx->root, true, false, 0);

  int finalScore = mx->miniMax(mx->root, n + m, true);

  Node::printNodes();
  cout << "score: " << finalScore << endl;
}