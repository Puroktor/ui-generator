using System.Reflection;
using UIConfigGenerator.Attributes;
using UIConfigGenerator.Config;

namespace UIConfigGenerator.Parser
{
    internal static class UIFieldFactory
    {
        public static UIField CreateField(Type fieldType, string displayName, string? codeName)
        {
            return CreateField(fieldType, displayName, codeName, []);
        }

        private static UIField CreateField(Type fieldType, string? displayName, string? codeName, HashSet<Type> parsedTypesSet)
        {
            if (parsedTypesSet.Contains(fieldType))
            {
                throw new ArgumentException("Circular element link: " + fieldType);
            }
            bool isRequired = true;
            Type? nullable = Nullable.GetUnderlyingType(fieldType);
            if (nullable != null)
            {
                isRequired = false;
                fieldType = nullable;
            }
            if (IsNumericType(fieldType))
            {
                return new UIField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.NUMBER, Required = isRequired };
            }
            else if (fieldType == typeof(string))
            {
                return new UIField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.TEXT, Required = isRequired };
            }
            else if (fieldType == typeof(bool))
            {
                return new UIField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.BOOL, Required = isRequired };
            }
            else if (fieldType.IsEnum)
            {
                var values = fieldType.GetMembers(BindingFlags.Public | BindingFlags.Static);
                var dict = new Dictionary<string, string>();
                foreach (var value in values)
                {
                    dict.Add(value.Name, GetDisplayName(value, value.Name));
                }
                return new UIEnumField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.ENUM, Required = isRequired, SubmitToDisplayValues = dict };
            }
            else if (fieldType.IsArray)
            {
                UIField element = CreateField(fieldType.GetElementType(), null, null, parsedTypesSet);
                return new UIListField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.LIST, Required = isRequired, Element = element };
            }
            else
            {
                var enumerable = fieldType.GetInterfaces().Where(t => t.IsGenericType && t.GetGenericTypeDefinition() == typeof(IEnumerable<>)).FirstOrDefault();
                if (enumerable != null)
                {
                    Type itemType = enumerable.GenericTypeArguments[0];
                    UIField element = CreateField(itemType, null, null, parsedTypesSet);
                    return new UIListField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.LIST, Required = isRequired, Element = element };
                }
                else
                {
                    List<UIField> uiInnerFields = [];
                    parsedTypesSet.Add(fieldType);
                    foreach (var innerField in fieldType.GetProperties())
                    {
                        string innerName = GetDisplayName(innerField, innerField.Name);
                        string innerCodeName = SanitizeCodeName(innerField.Name);
                        var uiInnerField = CreateField(innerField.PropertyType, innerName, innerCodeName, parsedTypesSet);
                        uiInnerFields.Add(uiInnerField);
                    }
                    return new UIClassField() { DisplayName = displayName, CodeName = codeName, FieldType = UIFieldType.CLASS, Required = isRequired, InnerFields = uiInnerFields };
                }
            }
        }

        public static bool IsNumericType(Type type)
        {
            if (type.IsEnum) return false;
            switch (Type.GetTypeCode(type))
            {
                case TypeCode.Byte:
                case TypeCode.SByte:
                case TypeCode.UInt16:
                case TypeCode.UInt32:
                case TypeCode.UInt64:
                case TypeCode.Int16:
                case TypeCode.Int32:
                case TypeCode.Int64:
                case TypeCode.Decimal:
                case TypeCode.Double:
                case TypeCode.Single:
                    return true;
                default:
                    return false;
            }
        }

        public static string GetDisplayName(MemberInfo field, string codeName)
        {
            var displayNameAttr = Attribute.GetCustomAttribute(field, typeof(DisplayNameAttribute)) as DisplayNameAttribute;
            return GetDisplayName(displayNameAttr, codeName);
        }

        public static string GetDisplayName(ParameterInfo field, string codeName)
        {
            var displayNameAttr = Attribute.GetCustomAttribute(field, typeof(DisplayNameAttribute)) as DisplayNameAttribute;
            return GetDisplayName(displayNameAttr, codeName);
        }

        private static string GetDisplayName(DisplayNameAttribute? displayNameAttr, string codeName)
        {
            return displayNameAttr != null ? displayNameAttr.Name : codeName;
        }

        public static string SanitizeCodeName(string codeName)
        {
            return char.ToLower(codeName[0]) + codeName[1..];
        }
    }
}
