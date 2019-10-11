#!/usr/bin/env python
# -*- coding: utf-8 -*-

""" Test simple B-Tree implementation.
    node values are provided as arguments.
"""
__author__ = "Murali Koppula"
__license__ = "MIT"

import argparse

from btnode import Node
from btree import BTree

def add(root=None, vals=[]) ->'Node':
    # Walk through the tree in a breadth-first fashion to add vals.
    #
    if not root and vals:
        root = Node(vals.pop(0))

    q = [root] if root else []

    while len(q) > 0:
        n = q.pop()

        if n.left:
            q.insert(0, n.left)
        elif vals:
            n.left = Node(vals.pop(0))
            q.insert(0, n.left)

        if n.right:
            q.insert(0, n.right)
        elif vals:
            n.right = Node(vals.pop(0))
            q.insert(0, n.right)

    return root

parser = argparse.ArgumentParser(description='Make a BTree using given list of values.')
parser.add_argument('vals', metavar='VALS', nargs='+',
                     help='comma separated values')
args = parser.parse_args()

root = add(vals=args.vals)
btree = BTree(root)
print("'{}'".format(btree))

