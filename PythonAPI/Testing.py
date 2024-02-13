
import torch.nn as nn
import torch


# 5 dimensional embedding representation for each card
# spades -> clubs -> diamonds -> hearts
# 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A
class CardModel(nn.Module):
    def __init__(self):
        super(CardModel, self).__init__()
        self.card_embedding= nn.Embedding(52, 5)  # 52 cards, 5 dimension embedding

    def forward(self, card_indices):
        return self.card_embedding(card_indices)

def card_to_index(card):
    # Define suits and ranks
    suits = ['s', 'c', 'd', 'h']
    ranks = ['2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A']
    
    # Split the card into rank and suit
    rank, suit = card[:-1], card[-1]
    
    # Calculate index
    suit_index = suits.index(suit)
    rank_index = ranks.index(rank)
    return suit_index * len(ranks) + rank_index


# One-hot encoding for action 
def encode_action(action):
    actions = ["FOLD", "CHECK", "CALL", "BET", "RAISE", "ALL IN"]
    return [1 if a == action else 0 for a in actions]

# One-hot encoding for size
def encode_size(size):
    sizes = ["0", "small", "normal", "big", "all in"]
    return [1 if a == size else 0 for a in sizes]



# creating a log entry 
def create_log_entry(action, size):
    action_vector = encode_action(action)
    size_vector = encode_size(size)
    return [action_vector] + [size_vector]


# total input tensor [2 cards representing hand] + [5 cards representing board] + [n logs representing the game log]
