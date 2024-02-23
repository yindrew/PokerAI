
import torch.nn as nn
import torch

# total input tensor [2 cards representing hand] + [5 cards representing board] + [n logs representing the game log]
# Each card is represented as a 5 dimensional embedding
# each log has a action (6 possibilities) and a size (5 possibilities)
# input tensor will have 35(7 cards * 5 dimensinoal embedding) representing the players hand and 165 (15 total actions * 11 action + size) representing the game log
# 200 total input vectors


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

    # Create an instance of CardModel and get the embedding
    return suit_index * len(ranks) + rank_index



 # [fold, call, check, bet small, bet medium, bet big, bet all in, raise small, raise medium, raise big, raise all in, all in]

# One-hot encoding for decision  
def encode_action(action):
    actions = ["FOLD", "CHECK", "CALL", "BET SMALL", "BET MEDIUM", "BET BIG", "BET ALL IN", "RAISE SMALL", "RAISE MEDIUM", "RAISE BIG", "RAISE ALL IN", "ALL IN"]
    return torch.tensor([1 if a == action else 0 for a in actions])



# converts the hand, board and gamelog  
def input_to_tensor(hand, board, game_log):

    # Encode hand and board, pads the board with -1 if not filled
    all_card_indices = torch.tensor([encode_card(card) for card in hand] + [encode_card(card) for card in board] + [-1] * (5 - len(board)))


    # Encode game log
    log_embeddings = torch.cat([encode_action(action) for action in game_log] + [torch.zeros(12) for _ in range(15 - len(game_log))]).flatten()

    # Concatenate all embeddings
    return torch.cat([all_card_indices, log_embeddings]).flatten()
