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
    def __init__(self, hidden_size, num_layers):
        super(PokerGRU, self).__init__()
        self.hidden_size = hidden_size
        self.num_layers = num_layers
        self.card_embeddings = nn.Embedding(52, 5) # 52 cards, 5 dimension embedding
        self.gru = nn.GRU(200, hidden_size, num_layers, batch_first=True) # 35 from cards (7 cards * 5 dimension) + (11 action and embedding * 15 total actions allowed)


        # Decision output layer
        self.output = nn.Linear(hidden_size, 12)  # 12 outputs  [fold, call, check, bet small, bet medium, bet big, bet all in, raise small, raise medium, raise big, raise all in, all in]


    def forward(self, input_tensor):
        
        # update the input tensor to use embeddings for card values instead of index
        card_indices = input_tensor[:7]
        game_log_data = input_tensor[7:]
        card_embeddings = torch.cat([self.card_embeddings(index.unsqueeze(0)) if index != -1 else torch.zeros(1, 5) for index in card_indices])
        input_tensor = torch.cat([card_embeddings.flatten(), game_log_data]).unsqueeze(0)  # Add batch dimension

        # Forward propagate the GRU
        out, _ = self.gru(input_tensor)

        # Decision output
        decision_out = self.fc_decision(out[:, -1, :])
        # If using CrossEntropyLoss, remove softmax here
        decision_probs = F.softmax(decision_out, dim=1)

        return decision_probs




hidden_size = 256  # Can be adjusted
num_layers = 3  # number of hidden layers

# Create the model
model = PokerGRU(hidden_size, num_layers)
