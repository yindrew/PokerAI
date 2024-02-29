import torch.nn as nn
import torch

# total input tensor [2 cards representing hand] + [5 cards representing board] + [n logs representing the game log]
# Each card is represented as a 5 dimensional embedding
# each log has a action (6 possibilities) and a size (5 possibilities)
# input tensor will have 35(7 cards * 5 dimensinoal embedding) representing the players hand and 165 (15 total actions * 12 action + size) representing the game log
# 200 total input vectors


#get the index of the card
def encode_card(card):
    # Define suits and ranks
    suits = ['s', 'c', 'd', 'h']
    ranks = ['2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A']
    rank, suit = card[0], card[1]
    
    # Calculate index
    suit_index = suits.index(suit)
    rank_index = ranks.index(rank)

    return suit_index * len(ranks) + rank_index


# getting a action from a value
def decode_action(action):
    actions = ["FOLD", "CHECK", "CALL", "BET SMALL", "BET MEDIUM", "BET BIG", "BET ALL IN", "RAISE SMALL", "RAISE MEDIUM", "RAISE BIG", "RAISE ALL IN"]
    return actions[action]


 
# One-hot encoding for decision and action
def encode_action(action):
    actions = ["FOLD", "CHECK", "CALL", "BET_SMALL", "BET_MEDIUM", "BET_BIG", "BET_ALL_IN", "RAISE_SMALL", "RAISE_MEDIUM", "RAISE_BIG", "RAISE_ALL_IN"]
    return torch.tensor([1 if a == action else 0 for a in actions])



