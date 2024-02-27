import unittest
from pokerGRU import PokerGRU
import torch.nn as nn
import torch

class test_pokerGRU(unittest.TestCase):
    def setUp(self):
        # Initialize the PokerGRU model
        self.model = PokerGRU(hidden_size=256, num_layers=3)

    def test_initialization(self):
        # Test that the model initializes its layers correctly
        self.assertIsInstance(self.model.gru, nn.GRU)
        self.assertIsInstance(self.model.output, nn.Linear)
        self.assertEqual(self.model.gru.input_size, 215)

    def test_forward_pass(self):
        # Random indices for the first 7 values (representing card indices)
        card_indices = torch.randint(0, 52, (7,))
        
        # The rest of the tensor, filled appropriately
        # Assuming these are additional features; adjust as per your model's requirement
        additional_features = torch.randn(180)  # 187 total size - 7 card indices

        # Combine to form the complete input tensor
        dummy_input = torch.cat([card_indices, additional_features])
        # Ensure the input tensor is correctly sized
        self.assertEqual(dummy_input.size(0), 187)

        # Perform the forward pass
        action, decision_probs = self.model(dummy_input)

        # Continue with the rest of your test...


        # Check if the output is as expected
        self.assertIsInstance(action, int)
        self.assertTrue(0 <= action < 12)  # Assuming 12 possible actions
        self.assertEqual(decision_probs.shape, (1, 12))
        self.assertTrue(torch.all(decision_probs >= 0) and torch.all(decision_probs <= 1))  # Probabilities between 0 and 1
        self.assertAlmostEqual(torch.sum(decision_probs).item(), 1.0, places=5)  # Sum of probabilities should be close to 1

if __name__ == '__main__':
    unittest.main()
