using UnityEngine;

public class BraintreeDropInCaller : MonoBehaviour
{
    #region Fields

    [Header("Client Settings")]
    [Multiline(3)]
    [SerializeField]
    private string _clientToken;
    
    [Header("Button Settings")]
    [SerializeField]
    private Vector2 _buttonSize;
    
    [SerializeField]
    private Vector2 _buttonMinOffset;
    
    [SerializeField]
    private Vector2 _buttonMaxOffset;
    
    private AndroidJavaClass _bdaClass;
    private AndroidJavaObject _bdaInstance;

    private Vector2Int _screenSize;
    private Rect _buttonRect;

    #endregion
    
    #region Unity Methods

    private void Awake()
    {
        _bdaClass = new AndroidJavaClass("com.synergy88.braintreedropin.BraintreeDropInActivity");
        _bdaInstance = _bdaClass.CallStatic<AndroidJavaObject>("getInstance");
        
        _screenSize = new Vector2Int(Screen.width, Screen.height);
        _buttonSize = new Vector2(
            Mathf.Clamp(_buttonSize.x, 0.0f, _screenSize.x),
            Mathf.Clamp(_buttonSize.y, 0.0f, _screenSize.y));
        _buttonRect = new Rect(
            _screenSize.x - _buttonSize.x + _buttonMinOffset.x,
            _screenSize.y - _buttonSize.y + _buttonMinOffset.y,
            _buttonSize.x + _buttonMaxOffset.x,
            _buttonSize.y + _buttonMaxOffset.y);
    }

    private void OnGUI()
    {
        if (GUI.Button(_buttonRect, "OpenDropIn"))
        {
            _bdaInstance?.Call("openDropIn", _clientToken);
        }
    }

    #endregion
}
