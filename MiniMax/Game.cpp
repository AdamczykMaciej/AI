#include <iostream>
#include <vector>
#include <algorithm>

#include "Game.h"

using namespace std;
extern int Bajtek = 0;
extern int Bitek = 0;
extern int totalScore = 0;

Node::Node()
{
    int noOfNalesniki = 0;
    int smakowitosc = 0;
    int score = 0;
    bool isMaxPlayer = true;
}

void Node::printNodes()
{
    for (Node *n : Node::nodes)
    {
        if (n->children.size() == 2)
            cout << n->smakowitosc << " children: " << n->children[0]->smakowitosc
                 << " " << n->children[1]->smakowitosc << " score " << n->score << endl;
        else if (n->children.size() == 1)
            cout << n->smakowitosc << " children: " << n->children[0]->smakowitosc << " score " << n->score << endl;
        else
            cout << n->smakowitosc << " children: "
                 << " score " << n->score << endl;
    }
}

MiniMax::MiniMax()
{
    root = new Node();
    root->score = 0;
}

void MiniMax::constructTree(vector<int> nalesniki_n, vector<int> nalesniki_m)
{
    addLeaf(root, nalesniki_n, nalesniki_m);
};

vector<Node*> Node::nodes;
void MiniMax::addLeaf(Node *parentNode, vector<int> nalesniki_n, vector<int> nalesniki_m)
{
    //2 stosy naleśników
    vector<int> nalesniki_n_old = nalesniki_n;
    vector<int> nalesniki_m_old = nalesniki_m;

    // we add nodes when our parent, current node doesn't have any, we add possible branches
    if (parentNode->children.size() == 0)
    {
        if (nalesniki_n.size() >= 1 && nalesniki_m.size() >= 1)
        {
            Node *node1 = new Node();
            node1->smakowitosc = nalesniki_n.back();
            Node::nodes.push_back(node1); //optional
            parentNode->children.push_back(node1);
            Node *node2 = new Node();
            node2->smakowitosc = nalesniki_m.back();
            Node::nodes.push_back(node2); // optional
            parentNode->children.push_back(node2);
        }
        else if (nalesniki_n.size() >= 1)
        {
            Node *node1 = new Node();
            node1->smakowitosc = nalesniki_n.back();
            Node::nodes.push_back(node1);
            parentNode->children.push_back(node1);
        }
        else if (nalesniki_m.size() >= 1)
        {
            Node *node2 = new Node();
            node2->smakowitosc = nalesniki_m.back();
            Node::nodes.push_back(node2);
            parentNode->children.push_back(node2);
        }
    }

    // after adding nodes, we move further by invoking recursion
    if (parentNode->children.size() >= 2)
    {
        nalesniki_n.pop_back();
        nalesniki_m.pop_back();
        parentNode->children[0]->score += parentNode->score;
        parentNode->children[0]->score += parentNode->score;
        addLeaf(parentNode->children[0], nalesniki_n, nalesniki_m_old);
        addLeaf(parentNode->children[1], nalesniki_n_old, nalesniki_m);
    }
    else if (parentNode->children.size() == 1)
    {
        if (nalesniki_n.size() > 1)
        {
            nalesniki_n.pop_back();
            addLeaf(parentNode->children[0], nalesniki_n, nalesniki_m_old);
        }
        else if (nalesniki_m.size() > 1)
        {
            nalesniki_m.pop_back();
            addLeaf(parentNode->children[0], nalesniki_n_old, nalesniki_m);
        }
    }

    //binary search tree
    /*
    else
    {
        cout << "Already added\n";
    }
    else if (root->smakowitosc < parentNode->smakowitosc)
    {
        if(parentNode->children[0]!= NULL){
            addLeafPrivate(smakowitosc, parentNode->children[0]);
        }
        else
        {

            Node* node = new Node();
            node->smakowitosc=smakowitosc;

            parentNode->children[0] = node;
        }

    }
    else if (root->smakowitosc > parentNode->smakowitosc)
    {
        if(parentNode->children[1] != NULL){
            addLeafPrivate(smakowitosc, parentNode->children[1]);
        }
        else
        {
            Node* node = new Node();
            node->smakowitosc=smakowitosc;

            parentNode->children[1] = node;
        }

    }
    */
}

// evaluating score of nodes (end nodes hold the fin score of the 1st player)
void MiniMax::evaluateScore(Node *parentNode, bool Bajtek, bool flag, int numOfIterations)
{
    // for first nodes I set their score values to their values of "smakowitosc"
    if (flag == true)
    {
        for (Node *child : parentNode->children)
        {
            child->score = child->smakowitosc;
            child->isMaxPlayer = true;
        }
        flag = false;
    }

    else if (flag == false)
    {
        for (Node *child : parentNode->children)
        {
            if (parentNode->isMaxPlayer)
                child->score = parentNode->score;
            else
                child->score = child->smakowitosc + parentNode->score;
        }
    }
    for (Node *child : parentNode->children)
    {
        if (parentNode->isMaxPlayer)
            child->isMaxPlayer = false;
        else
        {
            child->isMaxPlayer = true;
        }
        evaluateScore(child, Bajtek, false, numOfIterations);
    }
};

int MiniMax::miniMax(Node *current, int depth, bool maximizingPlayer)
{
    if (depth == 0)
    {
        cout << "Score of a chain: " << current->score << endl;
        return current->score;
    }

    if (maximizingPlayer)
    {
        int maxEval = -2001;
        for (Node *child : current->children)
        {
            int eval = miniMax(child, depth - 1, false);
            maxEval = max(maxEval, eval);
        }
        return maxEval;
    }
    else
    {
        int minEval = 2001;
        for (Node *child : current->children)
        {
            int eval = miniMax(child, depth - 1, true);
            minEval = min(minEval, eval);
        }
        return minEval;
    }
}