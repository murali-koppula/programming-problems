#!/usr/bin/env python
# -*- coding: utf-8 -*-

""" A simple B-Tree implementation with (de)serialization.
    Data types that nodes hold:
    int, float, complex, str, bytes, tuple, set, list, map, dict, bool, NoneType
    B-Tree is internally serialized and deserialized.
"""
__author__ = "Murali Koppula"
__license__ = "MIT"

import builtins
from enum import Enum

class Node(object):
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

class BTree(object):
    # Only basic types are supported;
    # tuple, list, set, dict not supported.

    __ntypes = Enum('ntypes', {'int': 1, 'float': 2, 'bool': 3, 'complex': 4,
                    'str': 5, 'NoneType': 6})
    __sep = '\0'

    def __init__(self, noderepr=None):
        """ BTree() is constructed either from a tree of nodes or a string.
        """
        self.__root = self.__objstr = None
        if noderepr:
            if type(noderepr) is Node:
                self.__root = noderepr
                self.__objstr = BTree.__serialize(noderepr)

            elif type(noderepr) is str:
                self.__root = BTree.__deserialize(noderepr)
                self.__objstr = noderepr

            else:
                raise TypeError("BTree(): Node or a (serialized) string expected.")

    def __serialize(node):
        # Walk the tree in a deapth-first fashion to serialize nodes.
        #
        assert node, "__serialize(): non-empty node expected."

        typenum = BTree.__ntypes[type(node.val).__name__].value
        objstr = str(typenum) + str(node.val) + BTree.__sep

        for n in [node.left, node.right]:
            objstr += BTree.__serialize(n) if n else BTree.__sep

        return objstr

    def __deserialize(objstr):
        assert objstr and objstr.endswith(BTree.__sep), ("__deserialize(): "
               "objstr must be non-empty and terminated by token separator.")

        def getval():
            i = 0
            while i < len(objstr):
                j = objstr.find(BTree.__sep, i)
                if (j == -1):
                    raise ValueError("getbal(): deserialization error at {}".format(i))

                (valstr,i) = (objstr[i:j], j + 1)
                yield valstr

        def mknode():
            valstr = next(gen)

            if valstr == '':
                node = None
            else:
                typename = BTree.__ntypes(int(valstr[0])).name

                if typename == 'NoneType':
                    val = None
                elif typename == 'bool':
                    val = valstr[1:] == 'True'
                else:
                    val = getattr(builtins, typename)(valstr[1:])

                lnode = mknode()
                rnode = mknode()
                node = Node(val, lnode, rnode)

            return node

        gen = getval()
        return mknode()

    def __repr__(self):
        return self.__objstr

    def __str__(self):
        return self.__objstr

    def list(self):
        # Walk the tree in a deapth-first fashion to list nodes.
        #
        def getlist(node):
            nodes = [(type(node.val).__name__, node.val)]

            for n in [node.left, node.right]:
                nodes += getlist(n) if n else []

            return nodes

        return getlist(self.__root)

def main():
    # Test case with nodes of different basic data types.
    #
    root = Node(1)
    (root.left, root.right) = (Node(0.24), Node(None))
    (root.left.left, root.left.right, root.right.left, root.right.right) = (
        Node(2+4j), Node(True), Node('abc'), Node(False))

    btree = BTree(root)
    btstr = str(btree)
    # print(btree.list())
    # print(btstr)

    st = "success" if btstr == str(BTree(btstr)) else "failed"
    print("Test " + st + ".")

if __name__ == "__main__":
    main()

