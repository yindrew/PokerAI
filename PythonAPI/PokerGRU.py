import torch
import torch.nn as nn
import torch.nn.functional as F


class PokerGRU(nn.Module):
    def __init__(self, input_size, hidden_size, num_layers):
        super(PokerGRU, self).__init__()
        self.hidden_size = hidden_size
        self.num_layers = num_layers
        self.gru = nn.GRU(input_size, hidden_size, num_layers, batch_first=True)

        # Decision output layer
        self.fc_decision = nn.Linear(hidden_size, 6)  # 6 actions

        # Size output layer 
        self.fc_size = nn.Linear(hidden_size, 5)  # 5 sizes

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
num_layers = 3 # number of hidden layers

# Create the model
model = PokerGRU(input_size, hidden_size, num_layers)


