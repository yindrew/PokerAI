import unittest
import torch
from encoder import encode_card, encode_action  # Adjust the import statement based on your project structure

class test_Encoder(unittest.TestCase):

    def test_encode_card(self):
        # Test the encode_card function
        # Example: Assert that the encoding for the card '2h' is correct
        self.assertEqual(encode_card('2s'), 0)
        self.assertEqual(encode_card('Ah'), 51)

    def test_encode_action(self):
        # Test the encode_action function
        # Example: Check if the tensor for 'FOLD' is correctly one-hot encoded
        expected_fold_encoding = torch.tensor([1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
        self.assertTrue(torch.equal(encode_action('FOLD'), expected_fold_encoding))


if __name__ == '__main__':
    unittest.main()

