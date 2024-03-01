import unittest
from pokerGRU import PokerGRU
import torch

class TestPokerGRU(unittest.TestCase):
    def setUp(self):
        self.model = PokerGRU(hidden_size=256, num_layers=3)
        self.input_tensor = torch.rand(1, 187)  # Mock input tensor of appropriate shape
        self.legal_moves = [1] * 11  # Mock legal moves

    def test_forward(self):
        action, decision_probs, predicted_value = self.model(self.input_tensor, self.legal_moves)
        self.assertIsInstance(action, int)
        self.assertEqual(len(decision_probs[0]), 11)
        self.assertTrue(torch.is_tensor(predicted_value))

    def test_process_final_state(self):
        final_state = torch.rand(1, 187)  # Mock final state tensor
        optimizer = torch.optim.Adam(self.model.parameters(), lr=0.001)
        loss = self.model.process_final_state(final_state, optimizer)
        self.assertTrue(loss >= 0)

if __name__ == '__main__':
    unittest.main()
