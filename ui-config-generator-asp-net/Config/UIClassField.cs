namespace UIConfigGenerator.Config
{
    internal class UIClassField : UIField
    {
        public required List<UIField> InnerFields { get; set; }
    }
}
