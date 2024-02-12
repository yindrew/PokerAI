
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



# One-hot encoding for actions [FOLD, CHECK, CALL, BET, RAISE, ALL in]
def encode_action(action_idx):
    actions = [0] * 6  
    actions[action_idx] = 1
    return actions

# One-hot encoding for action size [0, 1/3, 3/4, 2, all in]
def encode_size(size_idx):
    sizes = [0] * 5 
    sizes[size_idx] = 1
    return sizes


# creating a log entry 
def create_log_entry(action_idx, size_idx):
    action_vector = encode_action(action_idx)
    size_vector = encode_size(size_idx)
    return [action_vector] + [size_vector]

testing = create_log_entry(2, 3)
print(testing)




def main():
    # Your main code logic here
    print("Hello, world!")
    model = CardModel()
    card_index = torch.tensor([0])
    print(model.forward(card_index))


if __name__ == "__main__":
    main()



# total input tensor [2 cards representing hand] + [5 cards representing board] + [n logs representing the game log]
