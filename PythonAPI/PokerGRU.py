import torch
import torch.nn as nn
import torch.nn.functional as F
import Encoder as encoder
# 5 dimensional embedding representation for each card
# spades -> clubs -> diamonds -> hearts
# 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A
class CardModel(nn.Module):
    def __init__(self):
        super(CardModel, self).__init__()
        self.card_embedding= nn.Embedding(52, 5)  

    def forward(self, card_indices):
        return self.card_embedding(card_indices)
    


class PokerGRU(nn.Module):
    def __init__(self, input_size, hidden_size, num_layers):
        super(PokerGRU, self).__init__()
        self.hidden_size = hidden_size
        self.num_layers = num_layers
        self.card_embeddings = nn.Embedding(52, 5) # 52 cards, 5 dimension embedding
        self.gru = nn.GRU(input_size, hidden_size, num_layers, batch_first=True)

        # Decision output layer
        self.fc_decision = nn.Linear(hidden_size, 6)  # 6 actions [fold, check, call, bet, raise, all in]

        # Size output layer
        self.fc_size = nn.Linear(hidden_size, 5)  # 5 sizes [0, ]

    def forward(self, x):
        # Initializing hidden state for first input
        h0 = torch.zeros(self.num_layers, x.size(0), self.hidden_size).to(x.device)

        # Forward propagate the GRU
        out, _ = self.gru(x, h0)

        # Decisions
        decision_out = self.fc_decision(out[:, -1, :])
        decision_probs = F.softmax(decision_out, dim=1)

        # Sizes
        size_out = self.fc_size(out[:, -1, :])
        size_probs = F.softmax(size_out, dim=1)

        return decision_probs, size_probs


input_size = 200  # Length of your input vector
hidden_size = 256  # Can be adjusted
num_layers = 3  # number of hidden layers

# Create the model
model = PokerGRU(input_size, hidden_size, num_layers)
