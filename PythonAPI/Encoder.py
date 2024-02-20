
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


# get the embedding of the card
def encode_card(card):
    # Define suits and ranks
    suits = ['s', 'c', 'd', 'h']
    ranks = ['2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A']
    
    # Split the card into rank and suit
    rank, suit = card[0], card[1]
    
    # Calculate index
    suit_index = suits.index(suit)
    rank_index = ranks.index(rank)
    card_index = torch.tensor([suit_index * len(ranks) + rank_index])

    # Create an instance of CardModel and get the embedding
    model = CardModel()
    return model(card_index)



# One-hot encoding for action 
def encode_action(action):
    actions = ["FOLD", "CHECK", "CALL", "BET", "RAISE", "ALL IN"]
    return [1 if a == action else 0 for a in actions]

# One-hot encoding for size
def encode_size(size):
    sizes = ["0", "small", "normal", "big", "all in"]
    return [1 if a == size else 0 for a in sizes]

def encode_action_size(action, size):
    # Get one-hot encoded vectors for action and size
    action_vector = encode_action(action)
    size_vector = encode_size(size)

    # Concatenate the action and size vectors
    action_size_vector = action_vector + size_vector

    return torch.tensor(action_size_vector)

# total input tensor [2 cards representing hand] + [5 cards representing board] + [n logs representing the game log]
# Each card is represented as a 5 dimensional embedding
# each log has a action (6 possibilities) and a size (5 possibilities)
# input tensor will have 35(7 cards * 5 dimensinoal embedding) representing the players hand and 165 (15 total actions * 11 action + size) representing the game log
# 200 total input vectors

def input_to_tensor(hand, board, game_log, max_log_length):
    # Encode hand and board
    hand_embeddings = torch.cat([encode_card(card) for card in hand]).flatten()
    board_embeddings = torch.cat([encode_card(card) if card else torch.zeros(1, 5) for card in board + [None] * (5 - len(board))]).flatten()

    # Encode game log
    log_embeddings = torch.cat([encode_action_size(action, size) for action, size in game_log] + [torch.zeros(11) for _ in range(max_log_length - len(game_log))]).flatten()

    # Concatenate all embeddings
    return torch.cat([hand_embeddings, board_embeddings, log_embeddings])